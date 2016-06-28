package com.weizilla.workout.logger;

import com.weizilla.workout.logger.activity.ActivityManager;
import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.store.WorkoutStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component
public class WorkoutLogger
{
    private final WorkoutStore workoutStore;
    private final ActivityManager activityManager;

    @Autowired
    public WorkoutLogger(WorkoutStore workoutStore, ActivityManager activityManager)
    {
        this.workoutStore = workoutStore;
        this.activityManager = activityManager;
    }

    public void put(Workout workout)
    {
        workoutStore.put(workout);
    }

    public List<Workout> getAllWorkouts()
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

    public Set<String> getAllTypes()
    {
        return workoutStore.getAllTypes();
    }
}
