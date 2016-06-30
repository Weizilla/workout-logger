package com.weizilla.workout.logger;

import com.weizilla.workout.logger.entity.ManualEntry;
import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.garmin.GarminManager;
import com.weizilla.workout.logger.store.ManualEntryStore;
import com.weizilla.workout.logger.store.WorkoutStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component
public class WorkoutLogger
{
    private final WorkoutStore workoutStore;
    private final ManualEntryStore manualEntryStore;
    private final GarminManager garminManager;

    @Autowired
    public WorkoutLogger(WorkoutStore workoutStore, ManualEntryStore manualEntryStore, GarminManager garminManager)
    {
        this.workoutStore = workoutStore;
        this.manualEntryStore = manualEntryStore;
        this.garminManager = garminManager;
    }

    public void addEntry(ManualEntry entry)
    {
        manualEntryStore.put(entry);
        Workout workout = new Workout(null, entry.getType(), entry.getDuration(), entry.getDate(), entry.getEntryTime(),
            entry.getComment(), null, entry.getId());
        workoutStore.put(workout);
    }

    public void put(Workout workout)
    {
        workoutStore.put(workout);
    }

    public List<Workout> getAllWorkouts()
    {
        return workoutStore.getAll();
    }

    public Set<LocalDate> getAllDates()
    {
        return workoutStore.getAllDates();
    }

    public List<Workout> getForDate(LocalDate date)
    {
        return workoutStore.getForDate(date);
    }

    public Set<String> getAllTypes()
    {
        return workoutStore.getAllTypes();
    }
}
