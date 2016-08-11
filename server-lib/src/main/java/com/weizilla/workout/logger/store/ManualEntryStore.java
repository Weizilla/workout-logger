package com.weizilla.workout.logger.store;

import com.weizilla.workout.logger.entity.ManualEntry;

import java.util.List;

public interface ManualEntryStore
{
    void put(ManualEntry entry);
    List<ManualEntry> getAll();
    void deleteAll();
}
