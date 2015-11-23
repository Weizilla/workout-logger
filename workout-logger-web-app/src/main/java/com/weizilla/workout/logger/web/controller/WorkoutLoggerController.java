package com.weizilla.workout.logger.web.controller;

import com.weizilla.workout.logger.WorkoutLogger;
import com.weizilla.workout.logger.entity.Workout;
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

    @Autowired
    public WorkoutLoggerController(WorkoutLogger workoutLogger)
    {
        this.workoutLogger = workoutLogger;
    }

    @RequestMapping("/workouts")
    public List<Workout> getWorkouts()
    {
        return workoutLogger.getWorkouts();
    }

    @RequestMapping(path = "/workouts", method = RequestMethod.POST)
    public void addWorkout(@RequestBody Workout workout)
    {
        workoutLogger.store(workout);
    }
}
