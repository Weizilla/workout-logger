package com.weizilla.workout.logger.store;

import com.weizilla.workout.logger.entity.Workout;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public interface WorkoutStore
{
    void put(Workout workout);
    void delete(UUID id);
    void deleteAll();
    List<Workout> getAll();

    default Set<LocalDate> getAllDates()
    {
        return getAll().stream()
            .map(Workout::getDate)
            .collect(Collectors.toSet());
    }

    default List<Workout> getForDate(LocalDate localDate)
    {
        return getAll().stream()
            .filter(w -> w.getDate().equals(localDate))
            .collect(Collectors.toList());
    }

    default Set<String> getAllTypes()
    {
        return getAll().stream()
            .map(Workout::getType)
            .map(String::toLowerCase)
            .collect(Collectors.toSet());
    }
}
