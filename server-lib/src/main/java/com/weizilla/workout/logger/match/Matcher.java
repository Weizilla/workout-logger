package com.weizilla.workout.logger.match;

import com.weizilla.workout.logger.entity.GarminEntry;
import com.weizilla.workout.logger.entity.ManualEntry;
import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.store.GarminEntryStore;
import com.weizilla.workout.logger.store.ManualEntryStore;
import com.weizilla.workout.logger.store.WorkoutStore;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Collection;

//TODO put this in main WorkoutLogger class?
public class Matcher
{
    private final ByDateMatcher matcher;
    private final WorkoutStore workoutStore;
    private final GarminEntryStore garminStore;
    private final ManualEntryStore manualStore;

    @Autowired
    public Matcher(ByDateMatcher matcher, WorkoutStore workoutStore, GarminEntryStore garminStore,
        ManualEntryStore manualStore)
    {
        this.matcher = matcher;
        this.workoutStore = workoutStore;
        this.garminStore = garminStore;
        this.manualStore = manualStore;
    }

    public void match(LocalDate date)
    {
        Collection<Workout> workouts = workoutStore.getForDate(date);
        Collection<ManualEntry> manualEntries = manualStore.getForDate(date);
        Collection<GarminEntry> garminEntries = garminStore.getForDate(date);
        Collection<Workout> newWorkouts = matcher.match(workouts, manualEntries, garminEntries);
        workoutStore.putAll(newWorkouts);
    }

}
