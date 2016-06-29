package com.weizilla.workout.logger.store;

import com.weizilla.workout.logger.entity.ManualEntry;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

@Profile("memory")
@Component
public class MemoryManualEntryStore implements ManualEntryStore
{
    private final Map<UUID, ManualEntry> entries = new TreeMap<>();

    @Override
    public void put(ManualEntry entry)
    {
        entries.put(entry.getId(), entry);
    }

    @Override
    public List<ManualEntry> getAll()
    {
        return new ArrayList<>(entries.values());
    }
}