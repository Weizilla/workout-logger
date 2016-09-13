package com.weizilla.workout.logger;

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
import java.util.Set;

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

    public void put(Workout workout)
    {
        workoutStore.put(workout);
    }

    public Collection<Workout> getAllWorkouts()
    {
        return workoutStore.getAll();
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
}
