package com.weizilla.workout.logger.web.controller;

import com.weizilla.garmin.entity.Activity;
import com.weizilla.workout.logger.WorkoutLogger;
import com.weizilla.workout.logger.git.GitConfiguration;
import com.weizilla.workout.logger.entity.Export;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class WorkoutLoggerController
{
    private final WorkoutLogger workoutLogger;
    private final GitConfiguration gitConfiguration;

    @Autowired
    public WorkoutLoggerController(WorkoutLogger workoutLogger, GitConfiguration gitConfiguration)
    {
        this.workoutLogger = workoutLogger;
        this.gitConfiguration = gitConfiguration;
    }


    @RequestMapping(path = "/entry", method = RequestMethod.POST)
    public void addEntry(@RequestBody ManualEntry entry)
    {
        workoutLogger.addEntry(entry);
    }

    @RequestMapping(path = "/entry", method = RequestMethod.PUT)
    public void updateEntry(@RequestBody ManualEntry entry)
    {
        workoutLogger.updateEntry(entry);
    }

    @RequestMapping(path = "/entry/{id}", method = RequestMethod.GET)
    public ManualEntry getEntry(@PathVariable UUID id)
    {
        //TODO add 404 if id not found
        return workoutLogger.getManualEntry(id);
    }

    @RequestMapping("/workouts")
    public Collection<Workout> getWorkouts()
    {
        return workoutLogger.getAllWorkouts();
    }

    @RequestMapping(path = "/workouts/{id}", method = RequestMethod.DELETE)
    public void deleteWorkout(@PathVariable UUID id)
    {
        workoutLogger.deleteWorkout(id);
    }

    @RequestMapping(path = "/workouts/{id}", method = RequestMethod.GET)
    public Workout getWorkout(@PathVariable UUID id)
    {
        return workoutLogger.getWorkout(id);
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

    @RequestMapping(path = "/match/all")
    public Map<String, Object> matchAllDates()
    {
        workoutLogger.matchAllDates();
        return Collections.singletonMap("match", "success");
    }

    @RequestMapping(path = "/export")
    public Export exportAll()
    {
        return workoutLogger.exportAll();
    }

    @RequestMapping(path = "/git")
    public Map<String, String> gitConfiguration()
    {
        Map<String, String> result = new HashMap<>();
        result.put("buildTime", gitConfiguration.getBuildTime());
        result.put("commitIdAbbrev", gitConfiguration.getCommitIdAbbrev());
        return result;
    }
}
