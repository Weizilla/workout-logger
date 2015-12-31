package com.weizilla.workout.logger.store.mongo;

import com.weizilla.workout.logger.entity.Workout;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.core.Is.is;

@RunWith(MockitoJUnitRunner.class)
public class MongoStoreTest
{
    @Mock
    private WorkoutMongoRepo repo;
    private MongoWorkoutStore store;
    private Workout workout;
    private UUID id;

    @Before
    public void setUp() throws Exception
    {
        store = new MongoWorkoutStore(repo);
        workout = new Workout("TYPE", Duration.ofHours(1), LocalDate.now());
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
        assertThat(repo.findAll().size(), is(1));

        List<Workout> actual = store.getAll();
        assertThat(actual, is(sameInstance(workouts)));
    }
}