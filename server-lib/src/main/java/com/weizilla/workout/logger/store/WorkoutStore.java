package com.weizilla.workout.logger.store;

import com.weizilla.workout.logger.entity.Workout;

import java.util.List;
import java.util.UUID;

public interface WorkoutStore
{
    void put(Workout workout);
    void delete(UUID id);
    List<Workout> getAll();
}
