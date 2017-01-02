package com.weizilla.workout.logger.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Export
{
    private final List<Workout> workouts = new ArrayList<>();
    private final List<ManualEntry> manualEntries = new ArrayList<>();
    private final List<GarminEntry> garminEntries = new ArrayList<>();
    private final Instant generated;

    public Export(Collection<Workout> workouts, Collection<ManualEntry> manualEntries,
        Collection<GarminEntry> garminEntries)
    {
        this.workouts.addAll(workouts);
        this.manualEntries.addAll(manualEntries);
        this.garminEntries.addAll(garminEntries);
        generated = Instant.now();
    }

    public List<Workout> getWorkouts()
    {
        return Collections.unmodifiableList(workouts);
    }

    public List<ManualEntry> getManualEntries()
    {
        return Collections.unmodifiableList(manualEntries);
    }

    public List<GarminEntry> getGarminEntries()
    {
        return Collections.unmodifiableList(garminEntries);
    }

    public Instant getGenerated()
    {
        return generated;
    }
}
