package com.weizilla.workout.logger.store.mongo;

import com.weizilla.garmin.entity.Activity;
import com.weizilla.workout.logger.store.ActivityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("mongo")
@Component
public class MongoActivityStore implements ActivityStore
{
    private final MongoActivityRepository repo;

    @Autowired
    public MongoActivityStore(MongoActivityRepository repo)
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
}
