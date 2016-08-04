package com.weizilla.workout.logger.store;

import com.weizilla.garmin.entity.Activity;

import java.time.LocalDate;
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

    default List<Activity> getForDate(LocalDate date)
    {
        return getAll().stream()
            .filter(a -> a.getStart().toLocalDate().isEqual(date))
            .collect(Collectors.toList());
    }

    default void putAll(List<Activity> activities)
    {
        activities.forEach(this::put);
    }
}