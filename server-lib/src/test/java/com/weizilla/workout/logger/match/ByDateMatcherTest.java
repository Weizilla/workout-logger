package com.weizilla.workout.logger.match;

import com.google.common.collect.Iterables;
import com.weizilla.garmin.entity.Activity;
import com.weizilla.workout.logger.entity.GarminEntry;
import com.weizilla.workout.logger.entity.ManualEntry;
import com.weizilla.workout.logger.entity.ManualEntryStub;
import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.entity.WorkoutAssert;
import com.weizilla.workout.logger.entity.WorkoutState;
import com.weizilla.workout.logger.entity.WorkoutStub;
import com.weizilla.workout.logger.garmin.GarminEntryStub;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class ByDateMatcherTest
{
    private ByDateMatcher matcher;

    @Before
    public void setUp() throws Exception
    {
        matcher = new ByDateMatcher();
    }

    @Test
    public void returnsEmptyCollectionForNullAndEmpytInputs() throws Exception
    {
        Collection<Workout> workouts = matcher.match(null, null, null);
        assertThat(workouts).isEmpty();
    }

    @Test
    public void createWorkoutForNewManualEntries() throws Exception
    {
        ManualEntry manualEntry = ManualEntryStub.create();
        Collection<ManualEntry> manualEntries = Collections.singletonList(manualEntry);

        Collection<Workout> actual = matcher.match(null, manualEntries, null);
        assertThat(actual).hasSize(1);

        Workout workout = Iterables.getOnlyElement(actual);
        assertManualWorkout(manualEntry, workout);
    }

    @Test
    public void doNotCreateWorkoutForOldManualEntries() throws Exception
    {
        ManualEntry manualEntry = ManualEntryStub.create();
        Collection<ManualEntry> manualEntries = Collections.singletonList(manualEntry);

        Workout workout = WorkoutStub.create();
        workout.setManualId(manualEntry.getId());
        Collection<Workout> workouts = Collections.singletonList(workout);

        Collection<Workout> actual = matcher.match(workouts, manualEntries, null);
        assertThat(actual).isEmpty();
    }

    @Test
    public void doNotCreateWorkoutFromNewGarminEntry() throws Exception
    {
        Collection<GarminEntry> garminEntries = GarminEntryStub.createList();

        Collection<Workout> actual = matcher.match(null, null, garminEntries);
        assertThat(actual).isEmpty();
    }

    //
//    @Test
//    public void doNotCreateWorkoutIfManualEntryAlreadyMatched() throws Exception
//    {
//        ManualEntry entry = ManualEntryStub.create();
//        manualStore.put(entry);
//
//        Workout workout = new WorkoutBuilder()
//            .setManualId(entry.getId())
//            .build();
//        workoutStore.put(workout);
//
//        List<Workout> workouts = matcher.match(entry.getDate());
//        assertThat(workouts).isEmpty();
//    }

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