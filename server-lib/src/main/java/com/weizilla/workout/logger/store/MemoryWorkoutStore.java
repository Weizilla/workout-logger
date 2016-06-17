package com.weizilla.workout.logger.store;

import com.weizilla.workout.logger.entity.Workout;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Profile("memory")
@Component
public class MemoryWorkoutStore implements WorkoutStore
{
    private final Map<UUID, Workout> workouts = new HashMap<>();

    @Override
    public void put(Workout workout)
    {
        workouts.put(workout.getId(), workout);
    }

    @Override
    public void delete(UUID id)
    {
        workouts.remove(id);
    }

    @Override
    public List<Workout> getAll()
    {
        return new ArrayList<>(workouts.values());
    }

    @Override
    public Set<LocalDate> getAllDates()
    {
        return getAll().stream()
            .map(Workout::getDate)
            .collect(Collectors.toSet());
    }

    @Override
    public List<Workout> getForDate(LocalDate localDate)
    {
        return getAll().stream()
            .filter(w -> w.getDate().equals(localDate))
            .collect(Collectors.toList());
    }
}
