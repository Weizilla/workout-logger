package com.weizilla.workout.logger.store;

import com.google.common.collect.Lists;
import com.weizilla.garmin.entity.Activity;
import com.weizilla.workout.logger.garmin.ActivityStub;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class GarminEntryStoreTest
{
    private List<Activity> activities;
    private GarminEntryStore store;

    @Before
    public void setUp() throws Exception
    {
        store = new MemoryGarminEntryStore();
        activities = Lists.newArrayList(
            ActivityStub.create(LocalDateTime.now().plusDays(1)),
            ActivityStub.create(LocalDateTime.now()),
            ActivityStub.create(LocalDateTime.now().plusDays(1)),
            ActivityStub.create(LocalDateTime.now().plusMonths(1)));
        store.putAll(activities);
    }

    @Test
    public void returnsAllIds() throws Exception
    {
        Set<Long> expected = activities.stream()
            .map(Activity::getId)
            .collect(Collectors.toSet());
        Set<Long> actual = store.getIds();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getEntriesForDate() throws Exception
    {
        LocalDate input = activities.get(0).getStart().toLocalDate();
        List<Activity> actual = store.getForDate(input);
        assertThat(actual).hasSize(2);
        assertThat(actual).containsExactlyInAnyOrder(activities.get(0), activities.get(2));
    }

}