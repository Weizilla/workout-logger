package com.weizilla.workout.logger.store;

import com.weizilla.garmin.entity.Activity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface GarminEntryStore
{
    void put(Activity activity);
    List<Activity> getAll();
    void deleteAll();

    default Set<Long> getIds()
    {
        return getAll().stream().map(Activity::getId).collect(Collectors.toSet());
    }

    default void putAll(List<Activity> activities)
    {
        activities.forEach(this::put);
    }
}