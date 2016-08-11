package com.weizilla.workout.logger.store;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.entity.WorkoutBuilder;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkoutStoreTest
{
    private WorkoutStore store;

    @Before
    public void setUp() throws Exception
    {
        store = new MemoryWorkoutStore();
    }

    @Test
    public void getsAllWorkoutDates() throws Exception
    {
        LocalDate today = LocalDate.now();
        Set<LocalDate> dates = LongStream.range(0, 10)
            .mapToObj(today::plusDays)
            .collect(Collectors.toSet());
        Collection<Workout> workouts = dates.stream()
            .map(WorkoutStoreTest::createWorkout)
            .collect(Collectors.toList());
        store.putAll(workouts);

        Set<LocalDate> actual = store.getAllDates();
        assertThat(actual).isEqualTo(dates);
    }

    @Test
    public void getWorkoutsForSingleDate() throws Exception
    {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        Collection<Workout> workouts = Lists.newArrayList(createWorkout(today), createWorkout(tomorrow), createWorkout(today),
            createWorkout(tomorrow), createWorkout(today));
        store.putAll(workouts);

        Collection<Workout> actual = store.getForDate(today);
        assertThat(actual).hasSize(3);
    }

    @Test
    public void getsAllWorkoutTypes() throws Exception
    {
        Set<String> types = Sets.newHashSet("a", "b", "c");
        Collection<Workout> workouts = Lists.newArrayList(createWorkout("a"), createWorkout("b"), createWorkout("c"),
            createWorkout("b"), createWorkout("a"));
        store.putAll(workouts);

        Set<String> actual = store.getAllTypes();
        assertThat(actual).containsOnlyElementsOf(types);
    }

    @Test
    public void getsAllWorkoutsCaseInsensitive() throws Exception
    {
        Set<String> types = Sets.newHashSet("a", "b", "c");
        Collection<Workout> workouts = Lists.newArrayList(createWorkout("a"), createWorkout("B"), createWorkout("c"),
            createWorkout("b"), createWorkout("A"));
        store.putAll(workouts);

        Set<String> actual = store.getAllTypes();
        assertThat(actual).containsOnlyElementsOf(types);
    }

    private static Workout createWorkout(String type)
    {
        return new WorkoutBuilder()
            .setType(type)
            .setDuration(Duration.ofDays(1))
            .setComment("COMMENT")
            .setGarminId(1L)
            .setManualId(UUID.randomUUID())
            .build();
    }

    private static Workout createWorkout(LocalDate date)
    {
        return new WorkoutBuilder()
            .setType("TYPE")
            .setDuration(Duration.ofDays(1))
            .setDate(date)
            .setComment("COMMENT")
            .setGarminId(1L)
            .setManualId(UUID.randomUUID())
            .build();
    }
}