package com.weizilla.workout.logger.store;

import com.weizilla.workout.logger.entity.Workout;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface WorkoutStore
{
    void put(Workout workout);
    void delete(UUID id);
    List<Workout> getAll();
    Set<LocalDate> getAllDates();
    List<Workout> getForDate(LocalDate localDate);
}
