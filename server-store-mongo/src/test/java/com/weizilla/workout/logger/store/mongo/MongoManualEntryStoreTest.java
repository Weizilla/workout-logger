package com.weizilla.workout.logger.store.mongo;

import com.weizilla.workout.logger.entity.ManualEntry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MongoManualEntryStoreTest
{
    @Mock
    private MongoManualEntryRepository repo;
    private MongoManualEntryStore store;
    private ManualEntry entry;
    private UUID id;

    @Before
    public void setUp() throws Exception
    {
        store = new MongoManualEntryStore(repo);
        entry = new ManualEntry("TYPE", Duration.ofHours(1));
        id = entry.getId();
    }

    @Test
    public void putsManualEntryInRepo() throws Exception
    {
        store.put(entry);
        verify(repo).insert(entry);
    }

    @Test
    public void getsAllManualEntries() throws Exception
    {
        List<ManualEntry> entries = Collections.singletonList(entry);
        when(repo.findAll()).thenReturn(entries);
        assertThat(repo.findAll()).hasSize(1);

        List<ManualEntry> actual = store.getAll();
        assertThat(actual).isSameAs(entries);
    }
}