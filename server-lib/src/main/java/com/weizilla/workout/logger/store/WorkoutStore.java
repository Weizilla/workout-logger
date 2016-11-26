package com.weizilla.workout.logger.store;

import com.weizilla.workout.logger.entity.Workout;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public interface WorkoutStore extends Store<Workout, UUID>
{
    default Set<String> getAllTypes()
    {
        return getAll().stream()
            .map(Workout::getType)
            .collect(Collectors.toSet());
    }
}
