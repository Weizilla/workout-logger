package com.weizilla.workout.logger.get;

import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.store.WorkoutStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class GetWorkouts
{
    private final WorkoutStore workoutStore;

    @Autowired
    public GetWorkouts(WorkoutStore workoutStore)
    {
        this.workoutStore = workoutStore;
    }

    public List<Workout> getAll()
    {
        return workoutStore.getAll();
    }

    public List<Workout> getForDate(LocalDate date)
    {
        //TODO
        return null;
    }
}
