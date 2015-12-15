package com.weizilla.workout.logger.put;

import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.store.WorkoutStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PutWorkouts
{
    private final WorkoutStore workoutStore;

    @Autowired
    public PutWorkouts(WorkoutStore workoutStore)
    {
        this.workoutStore = workoutStore;
    }

    public void put(Workout workout)
    {
        workoutStore.put(workout);
    }
}
