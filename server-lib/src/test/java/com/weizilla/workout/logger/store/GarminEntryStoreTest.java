package com.weizilla.workout.logger.store;

import com.google.common.collect.Lists;
import com.weizilla.workout.logger.entity.GarminEntry;
import com.weizilla.workout.logger.garmin.GarminEntryStub;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class GarminEntryStoreTest
{
    private List<GarminEntry> entries;
    private GarminEntryStore store;

    @Before
    public void setUp() throws Exception
    {
        store = new MemoryGarminEntryStore();
        entries = Lists.newArrayList(
            GarminEntryStub.create(LocalDateTime.now().plusDays(1)),
            GarminEntryStub.create(LocalDateTime.now()),
            GarminEntryStub.create(LocalDateTime.now().plusDays(1)),
            GarminEntryStub.create(LocalDateTime.now().plusMonths(1)));
        store.putAll(entries);
    }

    @Test
    public void returnsAllIds() throws Exception
    {
        Set<Long> expected = entries.stream()
            .map(GarminEntry::getId)
            .collect(Collectors.toSet());
        Set<Long> actual = store.getIds();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getEntriesForDate() throws Exception
    {
        LocalDate input = entries.get(0).getDate();
        Collection<GarminEntry> actual = store.getForDate(input);
        assertThat(actual).hasSize(2);
        assertThat(actual).containsExactlyInAnyOrder(entries.get(0), entries.get(2));
    }

}