package com.weizilla.workout.logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.weizilla.workout.logger.entity.GarminEntry;
import com.weizilla.workout.logger.entity.ManualEntry;
import com.weizilla.workout.logger.entity.ManualEntryStub;
import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.entity.WorkoutAssert;
import com.weizilla.workout.logger.entity.WorkoutBuilder;
import com.weizilla.workout.logger.garmin.GarminEntryStub;
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

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
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
    private GarminEntry garminEntry;

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
        garminEntry = GarminEntryStub.create();
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
        WorkoutAssert.assertThat(addedWorkout).hasType(entry.getType());
        WorkoutAssert.assertThat(addedWorkout).isNotMatched();
        WorkoutAssert.assertThat(addedWorkout).hasDuration(entry.getDuration());
        WorkoutAssert.assertThat(addedWorkout).hasDate(entry.getDate());
        WorkoutAssert.assertThat(addedWorkout).hasEntryTime(entry.getEntryTime());
        WorkoutAssert.assertThat(addedWorkout).hasComment(entry.getComment());
        WorkoutAssert.assertThat(addedWorkout).hasManualId(entry.getId());
        WorkoutAssert.assertThat(addedWorkout).hasGarminId(Optional.empty());
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

        Collection<Workout> workouts = workoutLogger.getAllWorkouts();
        assertThat(workouts).hasSize(1);
        assertThat(workouts).containsExactly(workout);
    }

    @Test
    public void getWorkoutsForDate() throws Exception
    {
        LocalDate date = LocalDate.now();
        when(workoutStore.getForDate(date)).thenReturn(Lists.newArrayList(workout));

        Collection<Workout> workouts = workoutLogger.getForDate(date);
        assertThat(workouts).hasSize(1);
        assertThat(workouts).containsExactly(workout);
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
        Collection<GarminEntry> garminEntries = Collections.singletonList(garminEntry);
        when(garminManager.getAllEntries()).thenReturn(garminEntries);

        Collection<GarminEntry> actual = workoutLogger.getGarminEntries();
        assertThat(actual).isEqualTo(garminEntries);
    }

//    @Test
//    public void createWorkoutsFromNewGarminEntries() throws Exception
//    {
//        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
//        workoutLogger.setClock(clock);
//        Instant now = Instant.now(clock);
//
//        GarminEntry entry = GarminEntryStub.create();
//        Collection<GarminEntry> entries = Collections.singletonList(entry);
//        when(garminManager.refreshEntries()).thenReturn(entries);
//
//        int numDownloaded = workoutLogger.refreshGarminEntries();
//
//        assertThat(numDownloaded).isEqualTo(1);
//        verify(workoutStore).put(workoutCaptor.capture());
//
//        Workout addedWorkout = workoutCaptor.getValue();
//        assertThat(addedWorkout.getId()).isNotNull();
//        assertThat(addedWorkout.getType()).isEqualTo(entry.getActivity().getType());
//        assertThat(addedWorkout.isMatched()).isFalse();
//        assertThat(addedWorkout.getDuration()).isEqualTo(entry.getActivity().getDuration());
//        assertThat(addedWorkout.getDate()).isEqualTo(entry.getDate());
//        assertThat(addedWorkout.getEntryTime()).isEqualTo(now);
//        assertThat(addedWorkout.getComment()).isNull();
//        assertThat(addedWorkout.getManualId()).isNotPresent();
//        assertThat(addedWorkout.getGarminId()).isPresent().contains(entry.getId());
//    }
}