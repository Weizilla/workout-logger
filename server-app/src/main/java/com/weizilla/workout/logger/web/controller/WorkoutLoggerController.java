package com.weizilla.workout.logger.web.controller;

import com.weizilla.garmin.entity.Activity;
import com.weizilla.workout.logger.WorkoutLogger;
import com.weizilla.workout.logger.entity.GarminEntry;
import com.weizilla.workout.logger.entity.ManualEntry;
import com.weizilla.workout.logger.entity.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

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

    @RequestMapping("/entry")
    public void addEntry(@RequestBody ManualEntry entry)
    {
        workoutLogger.addEntry(entry);
    }

    @RequestMapping("/workouts")
    public Collection<Workout> getWorkouts()
    {
        return workoutLogger.getAllWorkouts();
    }

    @RequestMapping(path = "/workouts", method = RequestMethod.POST)
    public void addWorkout(@RequestBody Workout workout)
    {
        workoutLogger.put(workout);
    }

    @RequestMapping(path = "/workouts/dates")
    public Set<LocalDate> getWorkoutDates()
    {
        return workoutLogger.getAllDates();
    }

    @RequestMapping(path = "/workouts/dates/{date}")
    public Collection<Workout> getWorkoutsByDate(@PathVariable @DateTimeFormat(iso = ISO.DATE) LocalDate date)
    {
        return workoutLogger.getForDate(date);
    }

    @RequestMapping(path = "/workouts/types")
    public Set<String> getAllTypes()
    {
        return workoutLogger.getAllTypes();
    }

    @RequestMapping(path = "/garmin/entry")
    public Collection<GarminEntry> getGarminEntries()
    {
        return workoutLogger.getGarminEntries();
    }

    @RequestMapping(path = "/garmin/activity", method = RequestMethod.POST)
    public Map<String, Object> addGarminActivities(@RequestBody Collection<Activity> activities)
    {
        int added = workoutLogger.addGarminActivities(activities);
        return Collections.singletonMap("added", added);
    }

    @RequestMapping(path = "/garmin/refresh")
    public Map<String, Object> refreshGarminEntries()
    {
        return Collections.singletonMap("downloaded", workoutLogger.refreshGarminEntries());
    }
}
