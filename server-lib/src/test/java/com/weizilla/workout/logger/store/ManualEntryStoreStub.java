package com.weizilla.workout.logger.store;

import com.weizilla.workout.logger.entity.ManualEntry;

import java.util.Collections;
import java.util.List;

public class ManualEntryStoreStub implements ManualEntryStore
{
    private List<ManualEntry> entries = Collections.emptyList();

    public void setEntries(List<ManualEntry> entries)
    {
        this.entries = entries;
    }

    @Override
    public void put(ManualEntry entry)
    {

    }

    @Override
    public List<ManualEntry> getAll()
    {
        return entries;
    }

    @Override
    public void deleteAll()
    {

    }
}