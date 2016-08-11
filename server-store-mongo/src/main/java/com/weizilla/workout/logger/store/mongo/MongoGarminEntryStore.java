package com.weizilla.workout.logger.store.mongo;

import com.weizilla.garmin.entity.Activity;
import com.weizilla.workout.logger.store.GarminEntryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("mongo")
@Component
public class MongoGarminEntryStore implements GarminEntryStore
{
    private final MongoGarminEntryRepository repo;

    @Autowired
    public MongoGarminEntryStore(MongoGarminEntryRepository repo)
    {
        this.repo = repo;
    }

    @Override
    public void put(Activity activity)
    {
        repo.save(activity);
    }

    @Override
    public List<Activity> getAll()
    {
        return repo.findAll();
    }

    @Override
    public void deleteAll()
    {
        repo.deleteAll();
    }
}
