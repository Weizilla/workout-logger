package com.weizilla.workout.logger.store.mongo;

import com.weizilla.garmin.entity.Activity;
import com.weizilla.workout.logger.entity.GarminEntry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MongoGarminEntryStoreTest
{
    @Mock
    private MongoGarminEntryRepository repo;
    private MongoGarminEntryStore store;
    private GarminEntry entry;
    private long id;

    @Before
    public void setUp() throws Exception
    {
        store = new MongoGarminEntryStore(repo);
        id = 1234;
        entry = new GarminEntry(new Activity(id, "TYPE", Duration.ofDays(1), LocalDateTime.now(), 12.3));
    }

    @Test
    public void putsEntryInRepo() throws Exception
    {
        store.put(entry);
        verify(repo).save(entry);
    }

    @Test
    public void getsAllEntries() throws Exception
    {
        List<GarminEntry> activities = Collections.singletonList(entry);
        when(repo.findAll()).thenReturn(activities);
        assertThat(repo.findAll()).hasSize(1);

        List<GarminEntry> actual = store.getAll();
        assertThat(actual).isSameAs(activities);
    }

    @Test
    public void deletesAllFromStore() throws Exception
    {
        store.deleteAll();
        verify(repo).deleteAll();
    }
}