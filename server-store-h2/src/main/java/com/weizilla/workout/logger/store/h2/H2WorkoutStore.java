package com.weizilla.workout.logger.store.h2;

import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.store.WorkoutStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Profile("h2")
@Component
public class H2WorkoutStore implements WorkoutStore
{
    private final String path;

    @Autowired
    public H2WorkoutStore(@Value("${h2.path:/var/weizilla/workout-logger/h2-store}") String path)
    {
        this.path = path;
    }

    @Override
    public void put(Workout workout)
    {

    }

    @Override
    public void delete(UUID id)
    {

    }

    @Override
    public List<Workout> getAll()
    {
        return null;
    }

    public String getPath()
    {
        return path;
    }
}
