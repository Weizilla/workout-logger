package com.weizilla.workout.logger.store.mongo;

import com.weizilla.workout.logger.entity.ManualEntry;
import com.weizilla.workout.logger.store.ManualEntryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("mongo")
@Component
public class MongoManualEntryStore implements ManualEntryStore
{
    private final MongoManualEntryRepository repo;

    @Autowired
    public MongoManualEntryStore(MongoManualEntryRepository repo)
    {
        this.repo = repo;
    }

    @Override
    public void put(ManualEntry entry)
    {
        repo.insert(entry);
    }

    @Override
    public List<ManualEntry> getAll()
    {
        return repo.findAll();
    }
}
