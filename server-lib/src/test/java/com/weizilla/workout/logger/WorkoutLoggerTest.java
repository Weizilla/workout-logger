package com.weizilla.workout.logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.weizilla.workout.logger.entity.GarminEntry;
import com.weizilla.workout.logger.entity.ManualEntry;
import com.weizilla.workout.logger.entity.ManualEntryStub;
import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.entity.WorkoutBuilder;
import com.weizilla.workout.logger.garmin.GarminEntryStub;
import com.weizilla.workout.logger.garmin.GarminManager;
import com.weizilla.workout.logger.match.MatchRunner;
import com.weizilla.workout.logger.store.ManualEntryStore;
import com.weizilla.workout.logger.store.WorkoutStore;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
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
    @Mock
    private MatchRunner matchRunner;
    private WorkoutLogger workoutLogger;
    private Workout workout;

    @Before
    public void setUp() throws Exception
    {
        workoutLogger = new WorkoutLogger(workoutStore, manualEntryStore, garminManager, matchRunner);
        workout = new WorkoutBuilder()
            .setType("TYPE")
            .setDuration(Duration.ofDays(1))
            .setDate(LocalDate.now())
            .setEntryTime(Instant.now())
            .setComment("COMMENT")
            .setGarminId(1L)
            .setManualId(UUID.randomUUID())
            .build();
    }

    @Test
    public void addManualEntryAndRunMatcher() throws Exception
    {
        ManualEntry entry = ManualEntryStub.create();
        workoutLogger.addEntry(entry);
        verify(manualEntryStore).put(entry);
        verify(matchRunner).match(entry.getDate());
    }

    @Test
    public void runsMatcherAfterNewGarminEntries() throws Exception
    {
        GarminEntry entry = GarminEntryStub.create();
        Collection<GarminEntry> garminEntries = Collections.singleton(entry);
        when(garminManager.refreshEntries()).thenReturn(garminEntries);
        workoutLogger.refreshGarminEntries();
        verify(matchRunner).match(entry.getDate());
    }

    @Test
    public void getGarminEntries() throws Exception
    {
        Collection<GarminEntry> garminEntries = GarminEntryStub.createList();
        when(garminManager.getAllEntries()).thenReturn(garminEntries);

        Collection<GarminEntry> actual = workoutLogger.getGarminEntries();
        assertThat(actual).isEqualTo(garminEntries);
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
}