package com.weizilla.workout.logger.store;

import com.weizilla.workout.logger.entity.Workout;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class MemoryWorkoutStoreTest
{
    private static final UUID ID = UUID.randomUUID();
    private static final Workout WORKOUT = new Workout(ID, "TYPE", Duration.ofMinutes(45),
        LocalDate.now(), Instant.now(), "COMMENT");
    private MemoryWorkoutStore store;

    @Before
    public void setUp() throws Exception
    {
        store = new MemoryWorkoutStore();
    }

    @Test
    public void insertsAWorkout() throws Exception
    {
        assertThat(store.getAll()).isEmpty();

        store.put(WORKOUT);

        List<Workout> workouts = store.getAll();
        assertThat(workouts).hasSize(1);
        assertThat(workouts.get(0)).isSameAs(WORKOUT);
    }

    @Test
    public void deletesAWorkout() throws Exception
    {
        store.put(WORKOUT);
        assertThat(store.getAll()).hasSize(1);

        store.delete(ID);
        assertThat(store.getAll()).isEmpty();
    }
}