package com.weizilla.workout.logger.match;

import com.weizilla.workout.logger.entity.GarminEntry;
import com.weizilla.workout.logger.entity.ManualEntry;
import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.entity.WorkoutBuilder;
import com.weizilla.workout.logger.entity.WorkoutState;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ByDateMatcher
{
    public Collection<Workout> match(Collection<Workout> workoutsInput,
        Collection<ManualEntry> manualEntriesInput,
        Collection<GarminEntry> garminEntriesInput)
    {
        Collection<Workout> workouts = nullToEmpty(workoutsInput);
        Collection<ManualEntry> manualEntries = nullToEmpty(manualEntriesInput);
        Collection<GarminEntry> garminEntries = nullToEmpty(garminEntriesInput);

        Set<UUID> matchedEntryIds = workouts.stream()
            .map(Workout::getManualId)
            .map(Optional::get)
            .collect(Collectors.toSet());

        List<ManualEntry> unmatchedEntries = manualEntries.stream()
            .filter(e -> ! matchedEntryIds.contains(e.getId()))
            .collect(Collectors.toList());

        return unmatchedEntries.stream()
            .map(this::create)
            .collect(Collectors.toList());
    }

    private static <T> Collection<T>  nullToEmpty(Collection<T> input)
    {
        return input != null ? input : Collections.emptyList();
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
