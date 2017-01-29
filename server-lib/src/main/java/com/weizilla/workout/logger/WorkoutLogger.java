package com.weizilla.workout.logger;

import com.weizilla.garmin.entity.Activity;
import com.weizilla.workout.logger.entity.Export;
import com.weizilla.workout.logger.entity.GarminEntry;
import com.weizilla.workout.logger.entity.ManualEntry;
import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.garmin.GarminManager;
import com.weizilla.workout.logger.match.MatchRunner;
import com.weizilla.workout.logger.store.ManualEntryStore;
import com.weizilla.workout.logger.store.WorkoutStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
public class WorkoutLogger
{
    private final WorkoutStore workoutStore;
    private final ManualEntryStore manualEntryStore;
    private final GarminManager garminManager;
    private final MatchRunner matchRunner;

    @Autowired
    public WorkoutLogger(WorkoutStore workoutStore, ManualEntryStore manualEntryStore,
        GarminManager garminManager, MatchRunner matchRunner)
    {
        this.workoutStore = workoutStore;
        this.manualEntryStore = manualEntryStore;
        this.garminManager = garminManager;
        this.matchRunner = matchRunner;
    }

    public void addEntry(ManualEntry entry)
    {
        manualEntryStore.put(entry);
        matchRunner.match(entry.getDate());
    }

    public void updateEntry(ManualEntry entry)
    {
        entry.getWorkoutId().ifPresent(workoutStore::delete);
        manualEntryStore.put(entry);
        matchRunner.match(entry.getDate());
    }

    public ManualEntry getManualEntry(UUID id)
    {
        return manualEntryStore.get(id);
    }

    public Collection<Workout> getAllWorkouts()
    {
        return workoutStore.getAll();
    }

    public Workout getWorkout(UUID id)
    {
        return workoutStore.get(id);
    }

    public Set<LocalDate> getAllDates()
    {
        return workoutStore.getAllDates();
    }

    public Collection<Workout> getForDate(LocalDate date)
    {
        return workoutStore.getForDate(date);
    }

    public Set<String> getAllTypes()
    {
        return workoutStore.getAllTypes();
    }

    //TODO move garmin stuff into separate class?
    public Collection<GarminEntry> getGarminEntries()
    {
        return garminManager.getAllEntries();
    }

    public void deleteAllManualEntries()
    {
        manualEntryStore.deleteAll();
    }

    public void deleteAllWorkouts()
    {
        workoutStore.deleteAll();
    }

    public int refreshGarminEntries()
    {
        Collection<GarminEntry> newActivities = garminManager.refreshEntries();
        newActivities.stream()
            .map(GarminEntry::getDate)
            .forEach(matchRunner::match);
        return newActivities.size();
    }

    public int addGarminActivities(Collection<Activity> activities)
    {
        Collection<GarminEntry> newActivities = garminManager.addActivities(activities);
        newActivities.stream()
            .map(GarminEntry::getDate)
            .forEach(matchRunner::match);
        return newActivities.size();
    }


    public void matchAllDates()
    {
        Set<LocalDate> allDays = new HashSet<>();
        allDays.addAll(manualEntryStore.getAllDates());
        allDays.addAll(garminManager.getAllDates());
        allDays.forEach(matchRunner::match);
    }

    public Export exportAll()
    {
        return new Export(workoutStore.getAll(), manualEntryStore.getAll(), garminManager.getAllEntries());
    }

    public void deleteWorkout(UUID id)
    {
        Workout workout = workoutStore.get(id);
        if (workout != null)
        {
            UUID manualId = workout.getManualId();
            if (manualId != null)
            {
                manualEntryStore.delete(manualId);
            }
            workoutStore.delete(workout.getId());
        }
    }
}
