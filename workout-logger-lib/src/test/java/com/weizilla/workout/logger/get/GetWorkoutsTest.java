package com.weizilla.workout.logger.get;

import com.google.common.collect.Iterables;
import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.store.WorkoutStore;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetWorkoutsTest
{
    @Mock
    private WorkoutStore workoutStore;
    private GetWorkouts getWorkouts;
    private Workout workout;

    @Before
    public void setUp() throws Exception
    {
        getWorkouts = new GetWorkouts(workoutStore);
        workout = new Workout("SWIM", Duration.ofMinutes(10), LocalDate.now());
    }

    @Test
    public void returnsAllWorkoutsFromStore() throws Exception
    {
        when(workoutStore.getAll()).thenReturn(Collections.singletonList(workout));

        List<Workout> actual = getWorkouts.getAll();
        assertThat(actual.size(), is(1));
        assertThat(Iterables.getOnlyElement(actual), is(sameInstance(workout)));
    }

}