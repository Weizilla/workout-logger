package com.weizilla.workout.logger;

import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.store.WorkoutStore;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class WorkoutLogger
{
    private final WorkoutStore workoutStore;

    @Autowired
    public WorkoutLogger(WorkoutStore workoutStore)
    {
        this.workoutStore = workoutStore;
    }

    public void store(Workout workout)
    {
        workoutStore.store(workout);
    }

    public List<Workout> getWorkouts()
    {
        return workoutStore.getWorkouts();
    }
}
