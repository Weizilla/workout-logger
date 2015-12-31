package com.weizilla.workout.logger.store;

import com.weizilla.workout.logger.entity.Workout;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Profile("memory")
@Component
public class MemoryStore implements WorkoutStore
{
    private final Map<UUID, Workout> workouts = new ConcurrentHashMap<>();

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
        return Collections.unmodifiableList(new ArrayList<>(workouts.values()));
    }

    @Override
    public Set<LocalDate> getAllDates()
    {
        //TODO
        return Collections.emptySet();
    }
}
