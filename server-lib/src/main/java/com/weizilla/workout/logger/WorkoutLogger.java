package com.weizilla.workout.logger;

import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.get.GetWorkouts;
import com.weizilla.workout.logger.put.PutWorkouts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component
public class WorkoutLogger
{
    private final GetWorkouts getWorkouts;
    private final PutWorkouts putWorkouts;

    @Autowired
    public WorkoutLogger(GetWorkouts getWorkouts, PutWorkouts putWorkouts)
    {
        this.getWorkouts = getWorkouts;
        this.putWorkouts = putWorkouts;
    }

    public void put(Workout workout)
    {
        putWorkouts.put(workout);
    }

    public List<Workout> getAll()
    {
        return getWorkouts.getAll();
    }

    public Set<LocalDate> getAllDates()
    {
        return getWorkouts.getAllDates();
    }

    public List<Workout> getForDate(LocalDate date)
    {
        return getWorkouts.getForDate(date);
    }

    public Set<String> getAllTypes()
    {
        return getWorkouts.getAllTypes();
    }
}
