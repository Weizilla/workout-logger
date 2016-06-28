package com.weizilla.workout.logger.store.mongo;

import com.weizilla.garmin.entity.Activity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MongoActivityStoreTest
{
    @Mock
    private MongoActivityRepository repo;
    private MongoActivityStore store;
    private Activity activity;
    private long id;

    @Before
    public void setUp() throws Exception
    {
        store = new MongoActivityStore(repo);
        id = 1234;
        activity = new Activity(id, "TYPE", Duration.ofDays(1), Instant.now(), 12.3);
    }

    @Test
    public void putsActivityInRepo() throws Exception
    {
        store.put(activity);
        verify(repo).save(activity);
    }

    @Test
    public void getsAllActivities() throws Exception
    {
        List<Activity> activities = Collections.singletonList(activity);
        when(repo.findAll()).thenReturn(activities);
        assertThat(repo.findAll()).hasSize(1);

        List<Activity> actual = store.getAll();
        assertThat(actual).isSameAs(activities);
    }
}