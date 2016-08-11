package com.weizilla.workout.logger.store;

import com.google.common.collect.Lists;
import com.weizilla.garmin.entity.Activity;
import com.weizilla.workout.logger.garmin.ActivityStub;
import org.junit.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class GarminEntryStoreTest
{
    private static final List<Activity> ACTIVITIES = Lists.newArrayList(ActivityStub.create(),
        ActivityStub.create(), ActivityStub.create());

    @Test
    public void returnsAllIds() throws Exception
    {
        Set<Long> expected = ACTIVITIES.stream().map(Activity::getId).collect(Collectors.toSet());
        Set<Long> actual = new GarminEntryStoreStub().getIds();
        assertThat(actual).isEqualTo(expected);
    }

    private static class GarminEntryStoreStub implements GarminEntryStore
    {
        @Override
        public void put(Activity activity)
        {

        }

        @Override
        public List<Activity> getAll()
        {
            return ACTIVITIES;
        }

        @Override
        public void deleteAll()
        {

        }
    }
}