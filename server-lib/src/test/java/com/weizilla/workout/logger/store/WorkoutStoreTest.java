package com.weizilla.workout.logger.store;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.entity.WorkoutBuilder;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
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
            .map(WorkoutStoreTest::createWorkout)
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
        List<Workout> workouts = Lists.newArrayList(createWorkout(today), createWorkout(tomorrow), createWorkout(today),
            createWorkout(tomorrow), createWorkout(today));
        store.setWorkouts(workouts);

        List<Workout> actual = store.getForDate(today);
        assertThat(actual).hasSize(3);
    }

    @Test
    public void getsAllWorkoutTypes() throws Exception
    {
        Set<String> types = Sets.newHashSet("a", "b", "c");
        List<Workout> workouts = Lists.newArrayList(createWorkout("a"), createWorkout("b"), createWorkout("c"),
            createWorkout("b"), createWorkout("a"));
        store.setWorkouts(workouts);

        Set<String> actual = store.getAllTypes();
        assertThat(actual).containsOnlyElementsOf(types);
    }

    @Test
    public void getsAllWorkoutsCaseInsensitive() throws Exception
    {
        Set<String> types = Sets.newHashSet("a", "b", "c");
        List<Workout> workouts = Lists.newArrayList(createWorkout("a"), createWorkout("B"), createWorkout("c"),
            createWorkout("b"), createWorkout("A"));
        store.setWorkouts(workouts);

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

        @Override
        public void deleteAll()
        {

        }
    }
}