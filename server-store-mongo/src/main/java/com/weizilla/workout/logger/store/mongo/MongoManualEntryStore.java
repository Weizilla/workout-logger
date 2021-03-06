package com.weizilla.workout.logger.store.mongo;

import com.weizilla.workout.logger.entity.ManualEntry;
import com.weizilla.workout.logger.store.ManualEntryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

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
    public ManualEntry get(UUID uuid)
    {
        return repo.findOne(uuid);
    }

    @Override
    public void delete(UUID id)
    {
        repo.delete(id);
    }

    @Override
    public List<ManualEntry> getAll()
    {
        return repo.findAll();
    }

    @Override
    public void deleteAll()
    {
        repo.deleteAll();
    }
}
