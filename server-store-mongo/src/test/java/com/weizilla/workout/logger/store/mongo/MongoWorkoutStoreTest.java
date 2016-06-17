package com.weizilla.workout.logger.store.mongo;

import com.google.common.collect.Lists;
import com.weizilla.workout.logger.entity.Workout;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MongoWorkoutStoreTest
{
    @Mock
    private MongoWorkoutRepository repo;
    private MongoWorkoutStore store;
    private Workout workout;
    private UUID id;

    @Before
    public void setUp() throws Exception
    {
        store = new MongoWorkoutStore(repo);
        workout = new Workout("TYPE", Duration.ofHours(1));
        id = workout.getId();
    }

    @Test
    public void putsWorkoutInRepo() throws Exception
    {
        store.put(workout);
        verify(repo).insert(workout);
    }

    @Test
    public void deletesWorkoutFromRepo() throws Exception
    {
        store.delete(id);
        verify(repo).delete(id);
    }

    @Test
    public void getsAllWorkouts() throws Exception
    {
        List<Workout> workouts = Collections.singletonList(workout);
        when(repo.findAll()).thenReturn(workouts);
        assertThat(repo.findAll()).hasSize(1);

        List<Workout> actual = store.getAll();
        assertThat(actual).isSameAs(workouts);
    }

    @Test
    public void getsAllWorkoutDates() throws Exception
    {
        LocalDate today = LocalDate.now();
        Set<LocalDate> dates = LongStream.range(0, 10)
            .mapToObj(today::plusDays)
            .collect(Collectors.toSet());
        List<Workout> workouts = dates.stream()
            .map(d -> new Workout(UUID.randomUUID(), "TYPE", Duration.ofMinutes(45), d, Instant.now()))
            .collect(Collectors.toList());
        when(repo.findAll()).thenReturn(workouts);

        Set<LocalDate> actual = store.getAllDates();
        assertThat(actual).isEqualTo(dates);
    }

    @Test
    public void getWorkoutsForSingleDate() throws Exception
    {
        LocalDate today = LocalDate.now();
        List<Workout> workouts = Lists.newArrayList(
            new Workout(UUID.randomUUID(), "TYPE", Duration.ofMinutes(45), today, Instant.now()),
            new Workout(UUID.randomUUID(), "TYPE", Duration.ofMinutes(45), today, Instant.now()),
            new Workout(UUID.randomUUID(), "TYPE", Duration.ofMinutes(45), today, Instant.now()));
        when(repo.findByDate(today)).thenReturn(workouts);

        List<Workout> actual = store.getForDate(today);
        assertThat(actual).isEqualTo(workouts);
    }
}