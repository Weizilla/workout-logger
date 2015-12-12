package com.weizilla.workout.logger;

import com.google.common.collect.Lists;
import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.store.WorkoutStore;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WorkoutLoggerTest
{
    @Mock
    private WorkoutStore workoutStore;
    private WorkoutLogger workoutLogger;
    private Workout workout;

    @Before
    public void setUp() throws Exception
    {
        workoutLogger = new WorkoutLogger(workoutStore);
        workout = new Workout("TYPE", Duration.ofHours(1), LocalDate.now());
    }

    @Test
    public void storesInWorkoutStore() throws Exception
    {
        workoutLogger.store(workout);
        verify(workoutStore).put(workout);
    }

    @Test
    public void getsAllWorkouts() throws Exception
    {
        when(workoutStore.getAll()).thenReturn(Lists.newArrayList(workout));

        List<Workout> workouts = workoutLogger.getWorkouts();
        assertThat(workouts.size(), equalTo(1));
        assertThat(workouts.get(0), sameInstance(workout));
    }
}