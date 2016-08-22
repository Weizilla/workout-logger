package com.weizilla.workout.logger.match;

import com.weizilla.workout.logger.entity.GarminEntry;
import com.weizilla.workout.logger.entity.ManualEntry;
import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.entity.WorkoutBuilder;

import java.util.ArrayList;
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
        List<Workout> results = new ArrayList<>();

        Collection<Workout> workouts = nullToEmpty(workoutsInput);
        Collection<ManualEntry> manualEntries = nullToEmpty(manualEntriesInput);
        Collection<GarminEntry> garminEntries = nullToEmpty(garminEntriesInput);

        Set<UUID> matchedManualId = workouts.stream()
            .map(Workout::getManualId)
            .collect(Collectors.toSet());

        Set<Long> matchedGarminIds = workouts.stream()
            .map(Workout::getGarminId)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toSet());

        List<ManualEntry> unmatchedManualEntries = manualEntries.stream()
            .filter(e -> ! matchedManualId.contains(e.getId()))
            .collect(Collectors.toList());

        List<GarminEntry> unmatchedGarminEntries = garminEntries.stream()
            .filter(e -> ! matchedGarminIds.contains(e.getId()))
            .collect(Collectors.toList());

        for (ManualEntry manualEntry : unmatchedManualEntries)
        {
            Optional<GarminEntry> matchedEntry = popByType(unmatchedGarminEntries, manualEntry.getType());
            Long garminId = matchedEntry.isPresent() ? matchedEntry.get().getId() : null;
            Workout workout = create(manualEntry, garminId);
            results.add(workout);
        }

        for (Workout workout : workouts)
        {
            if (! workout.getGarminId().isPresent() && ! unmatchedGarminEntries.isEmpty())
            {
                //check for dup matches from previous
                Optional<GarminEntry> matchedEntry = popByType(unmatchedGarminEntries, workout.getType());
                matchedEntry.ifPresent(g -> {
                    workout.setGarminId(g.getId());
                    results.add(workout);
                });
            }
        }

        return results;
    }

    private static <T> Collection<T>  nullToEmpty(Collection<T> input)
    {
        return input != null ? input : Collections.emptyList();
    }

    private static Workout create(ManualEntry entry, Long garminId)
    {
        return new WorkoutBuilder()
            .setType(entry.getType())
            .setDuration(entry.getDuration())
            .setDate(entry.getDate())
            .setEntryTime(entry.getEntryTime())
            .setComment(entry.getComment())
            .setManualId(entry.getId())
            .setGarminId(garminId)
            .build();
    }

    private static Optional<GarminEntry> popByType(Collection<GarminEntry> entries, String type)
    {
        Optional<GarminEntry> found = entries.stream()
            .filter(g -> g.getActivity().getType().equalsIgnoreCase(type))
            .findFirst();
        found.ifPresent(entries::remove);
        return found;
    }
}
