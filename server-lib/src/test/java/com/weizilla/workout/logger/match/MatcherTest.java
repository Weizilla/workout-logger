package com.weizilla.workout.logger.match;

import com.weizilla.garmin.entity.Activity;
import com.weizilla.workout.logger.entity.ManualEntry;
import com.weizilla.workout.logger.entity.ManualEntryStub;
import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.entity.WorkoutAssert;
import com.weizilla.workout.logger.entity.WorkoutBuilder;
import com.weizilla.workout.logger.entity.WorkoutState;
import com.weizilla.workout.logger.store.GarminEntryStore;
import com.weizilla.workout.logger.store.ManualEntryStore;
import com.weizilla.workout.logger.store.MemoryGarminEntryStore;
import com.weizilla.workout.logger.store.MemoryManualEntryStore;
import com.weizilla.workout.logger.store.MemoryWorkoutStore;
import com.weizilla.workout.logger.store.WorkoutStore;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class MatcherTest
{
    private Matcher matcher;
    private WorkoutStore workoutStore;
    private GarminEntryStore garminStore;
    private ManualEntryStore manualStore;

    @Before
    public void setUp() throws Exception
    {
        workoutStore = new MemoryWorkoutStore();
        garminStore = new MemoryGarminEntryStore();
        manualStore = new MemoryManualEntryStore();
        matcher = new Matcher(workoutStore, garminStore, manualStore);
    }

    @Test
    public void createWorkoutForNewManualEntries() throws Exception
    {
        ManualEntry entry = ManualEntryStub.create();
        manualStore.put(entry);

        List<Workout> workouts = matcher.match(entry.getDate());
        assertThat(workouts).hasSize(1);

        Workout workout = workouts.get(0);
        assertManualWorkout(entry, workout);
    }

    @Test
    public void doNotCreateWorkoutIfManualEntryAlreadyMatched() throws Exception
    {
        ManualEntry entry = ManualEntryStub.create();
        manualStore.put(entry);

        Workout workout = new WorkoutBuilder()
            .setManualId(entry.getId())
            .build();
        workoutStore.put(workout);

        List<Workout> workouts = matcher.match(entry.getDate());
        assertThat(workouts).isEmpty();
    }

    private static void assertManualWorkout(ManualEntry entry, Workout workout)
    {
        assertThat(workout.getId()).isNotNull();
        WorkoutAssert.assertThat(workout).hasType(entry.getType());
        WorkoutAssert.assertThat(workout).hasState(WorkoutState.MANUAL);
        WorkoutAssert.assertThat(workout).hasDuration(entry.getDuration());
        WorkoutAssert.assertThat(workout).hasDate(entry.getDate());
        WorkoutAssert.assertThat(workout).hasEntryTime(entry.getEntryTime());
        WorkoutAssert.assertThat(workout).hasComment(entry.getComment());
        assertThat(workout.getManualId()).isPresent().contains(entry.getId());
    }

    private static void assertGarminWorkout(Activity activity, Workout workout)
    {
        assertThat(workout.getId()).isNotNull();
        WorkoutAssert.assertThat(workout).hasType(activity.getType());
        WorkoutAssert.assertThat(workout).hasState(WorkoutState.GARMIN);
        WorkoutAssert.assertThat(workout).hasDuration(activity.getDuration());
        WorkoutAssert.assertThat(workout).hasDate(activity.getStart().toLocalDate());
        WorkoutAssert.assertThat(workout).hasEntryTime(Instant.now());
        assertThat(workout.getComment()).isNull();
        assertThat(workout.getManualId()).isNotPresent();
        assertThat(workout.getGarminId()).isPresent().contains(activity.getId());
    }
}