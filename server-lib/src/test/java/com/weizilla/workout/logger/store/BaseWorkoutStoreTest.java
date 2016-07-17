package com.weizilla.workout.logger.store;

import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.entity.WorkoutBuilder;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
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
        workout = new WorkoutBuilder()
            .setType("TYPE")
            .setDuration(Duration.ofDays(1))
            .setDate(LocalDate.now())
            .setEntryTime(Instant.now())
            .setComment("COMMENT")
            .setGarminId(1L)
            .setManualId(UUID.randomUUID())
            .build();
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

        List<Workout> workouts = store.getAll();
        assertThat(workouts).hasSize(1);
        assertThat(workouts).containsExactly(workout);
    }

    @Test
    public void updatesWorkoutIfSameId() throws Exception
    {
        store.put(workout);

        List<Workout> workouts = store.getAll();
        assertThat(workouts).hasSize(1);
        assertThat(workouts).containsExactly(workout);

        Workout newWorkout = new WorkoutBuilder()
            .setId(id)
            .setType("NEW TYPE")
            .setDuration(Duration.ofDays(1))
            .setDate(LocalDate.now())
            .setEntryTime(Instant.now())
            .setComment("COMMENT")
            .setGarminId(1L)
            .setManualId(UUID.randomUUID())
            .build();
        store.put(newWorkout);

        List<Workout> newWorkouts = store.getAll();
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
