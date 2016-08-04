package com.weizilla.workout.logger.store;

import com.weizilla.workout.logger.entity.Workout;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class WorkoutStoreStub implements WorkoutStore
{
    private List<Workout> workouts = Collections.emptyList();

    public void setWorkouts(List<Workout> workouts)
    {
        this.workouts = workouts;
    }

    @Override
    public void put(Workout workout)
    {

    }

    @Override
    public void delete(UUID id)
    {

    }

    @Override
    public List<Workout> getAll()
    {
        return workouts;
    }

    @Override
    public void deleteAll()
    {

    }
}
