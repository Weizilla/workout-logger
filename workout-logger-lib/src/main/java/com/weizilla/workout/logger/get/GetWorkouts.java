package com.weizilla.workout.logger.get;

import com.weizilla.workout.logger.entity.Workout;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class GetWorkouts
{
    public List<Workout> getAll()
    {
        return null;
    }

    public List<Workout> getForDate(LocalDate date)
    {
        return null;
    }
}
