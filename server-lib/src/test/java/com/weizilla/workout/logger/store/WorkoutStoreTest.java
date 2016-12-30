package com.weizilla.workout.logger.store;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.entity.WorkoutStub;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.weizilla.workout.logger.entity.WorkoutStub.create;
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
            .map(WorkoutStub::create)
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
        Collection<Workout> workouts = Lists.newArrayList(create(today), create(tomorrow),
            create(today), create(tomorrow), create(today));
        store.putAll(workouts);

        Collection<Workout> actual = store.getForDate(today);
        assertThat(actual).hasSize(3);
    }

    @Test
    public void getsAllWorkoutTypes() throws Exception
    {
        Set<String> types = Sets.newHashSet("a", "b", "c");
        Collection<Workout> workouts = Lists.newArrayList(create("a"), create("b"), create("c"),
            create("b"), create("a"));
        store.putAll(workouts);

        Set<String> actual = store.getAllTypes();
        assertThat(actual).containsOnlyElementsOf(types);
    }
}