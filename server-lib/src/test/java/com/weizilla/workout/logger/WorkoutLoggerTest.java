package com.weizilla.workout.logger;

import com.google.common.collect.Lists;
import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.get.GetWorkouts;
import com.weizilla.workout.logger.put.PutWorkouts;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WorkoutLoggerTest
{
    @Mock
    private GetWorkouts getWorkouts;
    @Mock
    private PutWorkouts putWorkouts;
    private WorkoutLogger workoutLogger;
    private Workout workout;

    @Before
    public void setUp() throws Exception
    {
        workoutLogger = new WorkoutLogger(getWorkouts, putWorkouts);
        workout = new Workout("TYPE", Duration.ofHours(1), LocalDate.now());
    }

    @Test
    public void putsWorkout() throws Exception
    {
        workoutLogger.put(workout);
        verify(putWorkouts).put(workout);
    }

    @Test
    public void getsAllWorkouts() throws Exception
    {
        when(getWorkouts.getAll()).thenReturn(Lists.newArrayList(workout));

        List<Workout> workouts = workoutLogger.getAll();
        assertThat(workouts.size(), is(1));
        assertThat(workouts.get(0), is(sameInstance(workout)));
    }

    @Test
    public void getWorkoutsForDate() throws Exception
    {
        LocalDate date = LocalDate.now();
        when(getWorkouts.getForDate(date)).thenReturn(Lists.newArrayList(workout));

        List<Workout> workouts = workoutLogger.getForDate(date);
        assertThat(workouts.size(), is(1));
        assertThat(workouts.get(0), is(sameInstance(workout)));

    }
}