package com.weizilla.workout.logger.web.controller;

import com.weizilla.workout.logger.WorkoutLogger;
import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.sample.RandomDataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WorkoutLoggerController
{
    private final WorkoutLogger workoutLogger;
    private final RandomDataLoader randomDataLoader;

    @Autowired
    public WorkoutLoggerController(WorkoutLogger workoutLogger, RandomDataLoader randomDataLoader)
    {
        this.workoutLogger = workoutLogger;
        this.randomDataLoader = randomDataLoader;
    }

    @RequestMapping("/workouts")
    public List<Workout> getWorkouts()
    {
        return workoutLogger.getAll();
    }

    @RequestMapping(path = "/workouts", method = RequestMethod.POST)
    public void addWorkout(@RequestBody Workout workout)
    {
        workoutLogger.put(workout);
    }

    @RequestMapping(path = "/workouts/random", method = RequestMethod.POST)
    public void addRandomWorkout()
    {
        randomDataLoader.addWorkout();
    }
}
