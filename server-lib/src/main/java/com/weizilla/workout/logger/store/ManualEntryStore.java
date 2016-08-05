package com.weizilla.workout.logger.store;

import com.weizilla.workout.logger.entity.ManualEntry;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public interface ManualEntryStore
{
    void put(ManualEntry entry);
    List<ManualEntry> getAll();
    void deleteAll();

    //TODO combine with other entry stores
    default List<ManualEntry> getForDate(LocalDate date)
    {
        return getAll().stream()
            .filter(e -> e.getDate().isEqual(date))
            .collect(Collectors.toList());
    }
}
