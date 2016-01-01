package com.weizilla.workout.logger.store.mongo;

import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.store.WorkoutStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Profile("mongo")
@Component
public class MongoWorkoutStore implements WorkoutStore
{
    private final WorkoutMongoRepo repo;

    @Autowired
    public MongoWorkoutStore(WorkoutMongoRepo repo)
    {
        this.repo = repo;
    }

    @Override
    public void put(Workout workout)
    {
        repo.insert(workout);
    }

    @Override
    public void delete(UUID id)
    {
        repo.delete(id);
    }

    @Override
    public List<Workout> getAll()
    {
        return repo.findAll();
    }

    @Override
    public Set<LocalDate> getAllDates()
    {
        //TODO replace this with mongo aggregation
        return getAll().stream()
            .map(Workout::getDate)
            .collect(Collectors.toSet());
    }
}
