package com.weizilla.workout.logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.weizilla.garmin.entity.Activity;
import com.weizilla.workout.logger.entity.Export;
import com.weizilla.workout.logger.entity.GarminEntry;
import com.weizilla.workout.logger.entity.ManualEntry;
import com.weizilla.workout.logger.entity.ManualEntryStub;
import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.entity.WorkoutStub;
import com.weizilla.workout.logger.garmin.ActivityStub;
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

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
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
        workout = WorkoutStub.create();
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
    public void addGarminActivities() throws Exception
    {
        List<Activity> activities = ActivityStub.createList();
        GarminEntry entry = GarminEntryStub.create();
        Collection<GarminEntry> garminEntries = Collections.singleton(entry);
        when(garminManager.addActivities(activities)).thenReturn(garminEntries);

        workoutLogger.addGarminActivities(activities);

        verify(garminManager).addActivities(activities);
        verify(matchRunner).match(entry.getDate());
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
    public void exportsAllEntries() throws Exception
    {
        when(workoutStore.getAll()).thenReturn(Collections.singletonList(workout));
        GarminEntry garminEntry = GarminEntryStub.create();
        when(garminManager.getAllEntries()).thenReturn(Collections.singletonList(garminEntry));
        ManualEntry manualEntry = ManualEntryStub.create();
        when(manualEntryStore.getAll()).thenReturn(Collections.singletonList(manualEntry));

        Export export = workoutLogger.exportAll();
        assertThat(export.getWorkouts()).containsExactly(workout);
        assertThat(export.getGarminEntries()).containsExactly(garminEntry);
        assertThat(export.getManualEntries()).containsExactly(manualEntry);
    }

    @Test
    public void noErrorsDeletingInvalidIds() throws Exception {
        workoutLogger.deleteWorkout(UUID.randomUUID());
        verify(workoutStore, never()).delete(any(UUID.class));
        verify(manualEntryStore, never()).delete(any(UUID.class));
    }

    @Test
    public void deleteWorkoutAndManualWorkout() throws Exception {
        UUID workoutId = workout.getId();
        when(workoutStore.get(workoutId)).thenReturn(workout);

        workoutLogger.deleteWorkout(workoutId);
        verify(workoutStore).delete(workoutId);
        verify(manualEntryStore).delete(workout.getManualId());
    }

    @Test
    public void runsMatchForAllDates() throws Exception {
        LocalDate now = LocalDate.now();
        when(garminManager.getAllDates()).thenReturn(Sets.newHashSet(now, now.plusDays(1)));
        when(manualEntryStore.getAllDates()).thenReturn(Sets.newHashSet(now, now.minusDays(1)));
        workoutLogger.matchAllDates();
        verify(matchRunner).match(now);
        verify(matchRunner).match(now.plusDays(1));
        verify(matchRunner).match(now.minusDays(1));
    }
}