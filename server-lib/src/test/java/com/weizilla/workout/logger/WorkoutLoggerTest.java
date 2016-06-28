package com.weizilla.workout.logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.weizilla.workout.logger.activity.ActivityManager;
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
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WorkoutLoggerTest
{
    @Mock
    private WorkoutStore workoutStore;
    @Mock
    private ActivityManager activityManager;
    private WorkoutLogger workoutLogger;
    private Workout workout;

    @Before
    public void setUp() throws Exception
    {
        workoutLogger = new WorkoutLogger(workoutStore, activityManager);
        workout = new Workout("TYPE", Duration.ofHours(1));
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
}