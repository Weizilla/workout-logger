package com.weizilla.workout.logger.store;

import com.weizilla.workout.logger.entity.Workout;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MemoryStore implements WorkoutStore
{
    private final Map<UUID, Workout> workouts = new ConcurrentHashMap<>();

    @Override
    public void put(Workout workout)
    {
        workouts.put(workout.getUuid(), workout);
    }

    @Override
    public void delete(UUID uuid)
    {
        workouts.remove(uuid);
    }

    @Override
    public List<Workout> getAll()
    {
        return Collections.unmodifiableList(new ArrayList<>(workouts.values()));
    }
}
