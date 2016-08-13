package com.weizilla.workout.logger.match;

import com.weizilla.workout.logger.entity.GarminEntry;
import com.weizilla.workout.logger.entity.ManualEntry;
import com.weizilla.workout.logger.entity.ManualEntryStub;
import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.entity.WorkoutStub;
import com.weizilla.workout.logger.garmin.GarminEntryStub;
import com.weizilla.workout.logger.store.GarminEntryStore;
import com.weizilla.workout.logger.store.ManualEntryStore;
import com.weizilla.workout.logger.store.WorkoutStore;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Collection;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MatcherTest
{
    @Mock
    private ByDateMatcher byDateMatcher;
    @Mock
    private GarminEntryStore garminStore;
    @Mock
    private ManualEntryStore manualStore;
    @Mock
    private WorkoutStore workoutStore;
    private Matcher matcher;
    private Collection<Workout> workouts;
    private Collection<ManualEntry> manualEntries;
    private Collection<GarminEntry> garminEntries;
    private LocalDate now;

    @Before
    public void setUp() throws Exception
    {
        matcher = new Matcher(byDateMatcher, workoutStore, garminStore, manualStore);
        workouts = WorkoutStub.createList();
        manualEntries = ManualEntryStub.createList();
        garminEntries = GarminEntryStub.createList();
        now = LocalDate.now();
        when(garminStore.getForDate(now)).thenReturn(garminEntries);
        when(manualStore.getForDate(now)).thenReturn(manualEntries);
        when(workoutStore.getForDate(now)).thenReturn(workouts);
        when(byDateMatcher.match(workouts, manualEntries, garminEntries)).thenReturn(workouts);
    }

    @Test
    public void storesNewWorkouts() throws Exception
    {
        matcher.match(now);
        verify(workoutStore).putAll(workouts);
    }

    @Test
    public void callsMatcherWithProperArguments() throws Exception
    {
        matcher.match(now);
        verify(byDateMatcher).match(workouts, manualEntries, garminEntries);
    }
}