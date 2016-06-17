package com.weizilla.workout.logger.store;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.weizilla.workout.logger.entity.Workout;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkoutStoreTest
{
    private WorkoutStoreStub store;

    @Before
    public void setUp() throws Exception
    {
        store = new WorkoutStoreStub();
    }

    @Test
    public void getsAllWorkoutDates() throws Exception
    {
        LocalDate today = LocalDate.now();
        Set<LocalDate> dates = LongStream.range(0, 10)
            .mapToObj(today::plusDays)
            .collect(Collectors.toSet());
        List<Workout> workouts = dates.stream()
            .map(d -> new Workout(UUID.randomUUID(), "TYPE", Duration.ofMinutes(45), d, Instant.now(), "COMMENT"))
            .collect(Collectors.toList());
        store.setWorkouts(workouts);

        Set<LocalDate> actual = store.getAllDates();
        assertThat(actual).isEqualTo(dates);
    }

    @Test
    public void getWorkoutsForSingleDate() throws Exception
    {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        List<Workout> workouts = Lists.newArrayList(
            new Workout(UUID.randomUUID(), "TYPE", Duration.ofMinutes(45), today, Instant.now(), "COMMENT"),
            new Workout(UUID.randomUUID(), "TYPE", Duration.ofMinutes(45), tomorrow, Instant.now(), "COMMENT"),
            new Workout(UUID.randomUUID(), "TYPE", Duration.ofMinutes(45), today, Instant.now(), "COMMENT"),
            new Workout(UUID.randomUUID(), "TYPE", Duration.ofMinutes(45), tomorrow, Instant.now(), "COMMENT"),
            new Workout(UUID.randomUUID(), "TYPE", Duration.ofMinutes(45), today, Instant.now(), "COMMENT"));
        store.setWorkouts(workouts);

        List<Workout> actual = store.getForDate(today);
        assertThat(actual).hasSize(3);
    }

    @Test
    public void getsAllWorkoutTypes() throws Exception
    {
        Set<String> types = Sets.newHashSet("a", "b", "c");
        List<Workout> workouts = Lists.newArrayList(
            new Workout("a", Duration.ofMinutes(45)),
            new Workout("b", Duration.ofMinutes(45)),
            new Workout("c", Duration.ofMinutes(45)),
            new Workout("b", Duration.ofMinutes(45)),
            new Workout("a", Duration.ofMinutes(45)));
        store.setWorkouts(workouts);

        Set<String> actual = store.getAllTypes();
        assertThat(actual).containsOnlyElementsOf(types);
    }

    @Test
    public void getsAllWorkoutsCaseInsensitive() throws Exception
    {
        Set<String> types = Sets.newHashSet("a", "b", "c");
        List<Workout> workouts = Lists.newArrayList(
            new Workout("a", Duration.ofMinutes(45)),
            new Workout("B", Duration.ofMinutes(45)),
            new Workout("c", Duration.ofMinutes(45)),
            new Workout("b", Duration.ofMinutes(45)),
            new Workout("A", Duration.ofMinutes(45)));
        store.setWorkouts(workouts);

        Set<String> actual = store.getAllTypes();
        assertThat(actual).containsOnlyElementsOf(types);
    }

    private static class WorkoutStoreStub implements WorkoutStore
    {
        private List<Workout> workouts;

        private void setWorkouts(List<Workout> workouts)
        {
            this.workouts = workouts;
        }

        @Override
        public void put(Workout workout)
        {

        }

        @Override
        public void delete(UUID id)
        {

        }

        @Override
        public List<Workout> getAll()
        {
            return workouts;
        }
    }
}