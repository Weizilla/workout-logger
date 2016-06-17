package com.weizilla.workout.logger.get;

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
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
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
        workout = new Workout("SWIM", Duration.ofMinutes(10));
    }

    @Test
    public void returnsAllWorkoutsFromStore() throws Exception
    {
        when(workoutStore.getAll()).thenReturn(Collections.singletonList(workout));

        List<Workout> actual = getWorkouts.getAll();
        assertThat(actual).hasSize(1);
        assertThat(actual.get(0)).isSameAs(workout);
    }

    @Test
    public void returnsAllDates() throws Exception
    {
        LocalDate date = LocalDate.now();
        Set<LocalDate> dates = Collections.singleton(date);
        when(workoutStore.getAllDates()).thenReturn(dates);

        Set<LocalDate> actual = getWorkouts.getAllDates();
        assertThat(actual).isSameAs(dates);
    }

    @Test
    public void returnsAllWorkoutsForDate() throws Exception
    {
        LocalDate date = LocalDate.now();
        List<Workout> workouts = Collections.singletonList(workout);
        when(workoutStore.getForDate(date)).thenReturn(workouts);

        List<Workout> actual = getWorkouts.getForDate(date);
        assertThat(actual).isSameAs(workouts);
    }
}