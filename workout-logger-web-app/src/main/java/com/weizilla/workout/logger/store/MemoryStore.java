package com.weizilla.workout.logger.store;

import com.weizilla.workout.logger.entity.Workout;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class MemoryStore implements WorkoutStore
{
    private final List<Workout> workouts = new CopyOnWriteArrayList<>();

    @Override
    public void store(Workout workout)
    {
        workouts.add(workout);
    }

    @Override
    public List<Workout> getWorkouts()
    {
        return Collections.unmodifiableList(workouts);
    }
}
