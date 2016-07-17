package com.weizilla.workout.logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.weizilla.garmin.entity.Activity;
import com.weizilla.workout.logger.entity.ManualEntry;
import com.weizilla.workout.logger.entity.ManualEntryStub;
import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.entity.WorkoutBuilder;
import com.weizilla.workout.logger.garmin.ActivityStub;
import com.weizilla.workout.logger.garmin.GarminManager;
import com.weizilla.workout.logger.store.ManualEntryStore;
import com.weizilla.workout.logger.store.WorkoutStore;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WorkoutLoggerTest
{
    @Mock
    private WorkoutStore workoutStore;
    @Mock
    private GarminManager garminManager;
    @Mock
    private ManualEntryStore manualEntryStore;
    @Captor
    private ArgumentCaptor<Workout> workoutCaptor;
    private WorkoutLogger workoutLogger;
    private Workout workout;
    private Activity garminEntry;

    @Before
    public void setUp() throws Exception
    {
        workoutLogger = new WorkoutLogger(workoutStore, manualEntryStore, garminManager);
        workout = new WorkoutBuilder()
            .setType("TYPE")
            .setDuration(Duration.ofDays(1))
            .setDate(LocalDate.now())
            .setEntryTime(Instant.now())
            .setComment("COMMENT")
            .setGarminId(1L)
            .setManualId(UUID.randomUUID())
            .build();
        garminEntry = ActivityStub.create();
    }

    @Test
    public void addingManualEntryAddsWorkout() throws Exception
    {
        ManualEntry entry = ManualEntryStub.create();
        workoutLogger.addEntry(entry);

        verify(manualEntryStore).put(entry);
        verify(workoutStore).put(workoutCaptor.capture());

        Workout addedWorkout = workoutCaptor.getValue();
        assertThat(addedWorkout.getId()).isNotNull();
        assertThat(addedWorkout.getType()).isEqualTo(entry.getType());
        assertThat(addedWorkout.getDuration()).isEqualTo(entry.getDuration());
        assertThat(addedWorkout.getDate()).isEqualTo(entry.getDate());
        assertThat(addedWorkout.getEntryTime()).isEqualTo(entry.getEntryTime());
        assertThat(addedWorkout.getComment()).isEqualTo(entry.getComment());
        assertThat(addedWorkout.getManualId()).isPresent().contains(entry.getId());
    }

    @Test
    public void putsWorkout() throws Exception
    {
        workoutLogger.put(workout);
        verify(workoutStore).put(workout);
    }

    @Test
    public void getsAllWorkouts() throws Exception
    {
        when(workoutStore.getAll()).thenReturn(Lists.newArrayList(workout));

        List<Workout> workouts = workoutLogger.getAllWorkouts();
        assertThat(workouts).hasSize(1);
        assertThat(workouts.get(0)).isSameAs(workout);
    }

    @Test
    public void getWorkoutsForDate() throws Exception
    {
        LocalDate date = LocalDate.now();
        when(workoutStore.getForDate(date)).thenReturn(Lists.newArrayList(workout));

        List<Workout> workouts = workoutLogger.getForDate(date);
        assertThat(workouts).hasSize(1);
        assertThat(workouts.get(0)).isSameAs(workout);
    }

    @Test
    public void getAllWorkoutDates() throws Exception
    {
        LocalDate date = LocalDate.now();
        Set<LocalDate> dates = Sets.newHashSet(date);
        when(workoutStore.getAllDates()).thenReturn(dates);

        Set<LocalDate> actual = workoutLogger.getAllDates();
        assertThat(actual).isSameAs(dates);
    }

    @Test
    public void getAllTypes() throws Exception
    {
        Set<String> types = Sets.newHashSet("a", "b", "c");
        when(workoutStore.getAllTypes()).thenReturn(types);

        Set<String> actual = workoutLogger.getAllTypes();
        assertThat(actual).isSameAs(types);
    }

    @Test
    public void getGarminEntries() throws Exception
    {
        List<Activity> garminEntries = Collections.singletonList(garminEntry);
        when(garminManager.getAllEntries()).thenReturn(garminEntries);

        List<Activity> actual = workoutLogger.getGarminEntries();
        assertThat(actual).isEqualTo(garminEntries);
    }

    @Test
    public void createWorkoutsFromNewGarminEntires() throws Exception
    {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        workoutLogger.setClock(clock);
        Instant now = Instant.now(clock);

        Activity activity = ActivityStub.create();
        List<Activity> activities = Collections.singletonList(activity);
        when(garminManager.refreshEntries()).thenReturn(activities);

        int numDownloaded = workoutLogger.refreshGarminEntries();

        assertThat(numDownloaded).isEqualTo(1);
        verify(workoutStore).put(workoutCaptor.capture());

        LocalDate expectedDate = activity.getStart().atZone(WorkoutLogger.DEFAULT_TZ).toLocalDate();

        Workout addedWorkout = workoutCaptor.getValue();
        assertThat(addedWorkout.getId()).isNotNull();
        assertThat(addedWorkout.getType()).isEqualTo(activity.getType());
        assertThat(addedWorkout.getDuration()).isEqualTo(activity.getDuration());
        assertThat(addedWorkout.getDate()).isEqualTo(expectedDate);
        assertThat(addedWorkout.getEntryTime()).isEqualTo(now);
        assertThat(addedWorkout.getComment()).isNull();
        assertThat(addedWorkout.getManualId()).isNotPresent();
        assertThat(addedWorkout.getGarminId()).isPresent().contains(activity.getId());
    }
}