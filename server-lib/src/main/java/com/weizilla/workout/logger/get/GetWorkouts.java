package com.weizilla.workout.logger.get;

import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.store.WorkoutStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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

    public Set<LocalDate> getAllDates()
    {
        return workoutStore.getAllDates();
    }

    public List<Workout> getForDate(LocalDate date)
    {
        return workoutStore.getForDate(date);
    }
}
