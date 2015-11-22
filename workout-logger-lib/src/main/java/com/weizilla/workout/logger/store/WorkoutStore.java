package com.weizilla.workout.logger.store;

import com.weizilla.workout.logger.entity.Workout;

import java.util.List;

public interface WorkoutStore
{
    void store(Workout workout);
    List<Workout> getWorkouts();
}
