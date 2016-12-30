package com.weizilla.workout.logger.store;

import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.entity.WorkoutStub;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class BaseWorkoutStoreTest
{
    protected WorkoutStore store;
    protected Workout workout;
    private UUID id;

    @Before
    public void setUp() throws Exception
    {
        workout = WorkoutStub.create();
        id = workout.getId();
    }

    @Test
    public void workoutStoreIsSet() throws Exception
    {
        assertThat(store).isNotNull();
    }

    @Test
    public void putsWorkoutInStore() throws Exception
    {
        assertThat(store.getAll()).isEmpty();
        store.put(workout);

        Collection<Workout> workouts = store.getAll();
        assertThat(workouts).hasSize(1);
        assertThat(workouts).containsExactly(workout);
    }

    @Test
    public void updatesWorkoutIfSameId() throws Exception
    {
        store.put(workout);

        Collection<Workout> workouts = store.getAll();
        assertThat(workouts).hasSize(1);
        assertThat(workouts).containsExactly(workout);

        Workout newWorkout = WorkoutStub.create(id);
        store.put(newWorkout);

        Collection<Workout> newWorkouts = store.getAll();
        assertThat(newWorkouts).hasSize(1);
        assertThat(newWorkouts).containsExactly(newWorkout);
        assertThat(newWorkouts).doesNotContain(workout);
    }

    @Test
    public void deletesWorkout() throws Exception
    {
        store.put(workout);
        assertThat(store.getAll()).hasSize(1);

        store.delete(id);
        assertThat(store.getAll()).isEmpty();
    }

    @Test
    public void nothingIsDeletedIfDifferentIds() throws Exception
    {
        store.put(workout);
        assertThat(store.getAll()).hasSize(1);

        store.delete(UUID.randomUUID());
        assertThat(store.getAll()).hasSize(1);
    }
}
