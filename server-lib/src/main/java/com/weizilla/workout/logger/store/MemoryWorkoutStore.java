package com.weizilla.workout.logger.store;

import com.weizilla.workout.logger.entity.Workout;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Profile("memory")
@Component
public class MemoryWorkoutStore implements WorkoutStore
{
    private final Map<UUID, Workout> workouts = new HashMap<>();

    @Override
    public void put(Workout workout)
    {
        workouts.put(workout.getId(), workout);
    }

    @Override
    public void delete(UUID id)
    {
        workouts.remove(id);
    }

    @Override
    public List<Workout> getAll()
    {
        return new ArrayList<>(workouts.values());
    }

    @Override
    public void deleteAll()
    {
        workouts.clear();
    }
}
