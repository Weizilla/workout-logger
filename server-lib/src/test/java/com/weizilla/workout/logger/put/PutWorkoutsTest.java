package com.weizilla.workout.logger.put;

import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.store.WorkoutStore;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Duration;
import java.time.LocalDate;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PutWorkoutsTest
{
    @Mock
    private WorkoutStore workoutStore;
    private PutWorkouts putWorkouts;
    private Workout workout;

    @Before
    public void setUp() throws Exception
    {
        putWorkouts = new PutWorkouts(workoutStore);
        workout = new Workout("SWIM", Duration.ofMinutes(10), LocalDate.now());
    }

    @Test
    public void addsWorkoutToStore() throws Exception
    {
        putWorkouts.put(workout);
        verify(workoutStore).put(workout);
    }
}