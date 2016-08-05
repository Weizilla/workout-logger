package com.weizilla.workout.logger.match;

import com.weizilla.workout.logger.entity.ManualEntry;
import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.entity.WorkoutBuilder;
import com.weizilla.workout.logger.entity.WorkoutState;
import com.weizilla.workout.logger.store.GarminEntryStore;
import com.weizilla.workout.logger.store.ManualEntryStore;
import com.weizilla.workout.logger.store.WorkoutStore;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Matcher
{
    private final WorkoutStore workoutStore;
    private final GarminEntryStore garminStore;
    private final ManualEntryStore manualStore;

    @Autowired
    public Matcher(WorkoutStore workoutStore,
        GarminEntryStore garminStore, ManualEntryStore manualStore)
    {
        this.workoutStore = workoutStore;
        this.garminStore = garminStore;
        this.manualStore = manualStore;
    }

    public List<Workout> match(LocalDate date)
    {
        Set<UUID> matchedEntryIds = workoutStore.getForDate(date).stream()
            .map(Workout::getManualId)
            .map(Optional::get)
            .collect(Collectors.toSet());

        List<ManualEntry> unmatchedEntries = manualStore.getForDate(date).stream()
            .filter(e -> ! matchedEntryIds.contains(e.getId()))
            .collect(Collectors.toList());

        return unmatchedEntries.stream()
            .map(this::create)
            .collect(Collectors.toList());
    }

    private Workout create(ManualEntry entry)
    {
        return new WorkoutBuilder()
            .setType(entry.getType())
            .setState(WorkoutState.MANUAL)
            .setDuration(entry.getDuration())
            .setDate(entry.getDate())
            .setEntryTime(entry.getEntryTime())
            .setComment(entry.getComment())
            .setManualId(entry.getId())
            .build();
    }
}
