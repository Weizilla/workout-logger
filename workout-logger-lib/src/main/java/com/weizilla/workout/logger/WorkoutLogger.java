package com.weizilla.workout.logger;

import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.store.WorkoutStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
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
        workoutStore.put(workout);
    }

    public List<Workout> getWorkouts()
    {
        return workoutStore.getAll();
    }
}
