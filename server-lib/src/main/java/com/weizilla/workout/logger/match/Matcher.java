package com.weizilla.workout.logger.match;

import com.weizilla.workout.logger.entity.GarminEntry;
import com.weizilla.workout.logger.entity.ManualEntry;
import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.entity.WorkoutBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class Matcher
{
    private final MatcherConfiguration configuration;

    @Autowired
    public Matcher(MatcherConfiguration configuration)
    {
        this.configuration = configuration;
    }

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
            .flatMap(w -> w.getGarminIds().stream())
            .collect(Collectors.toSet());

        List<ManualEntry> unmatchedManualEntries = manualEntries.stream()
            .filter(e -> ! matchedManualId.contains(e.getId()))
            .collect(Collectors.toList());

        List<GarminEntry> unmatchedGarminEntries = garminEntries.stream()
            .filter(e -> ! matchedGarminIds.contains(e.getId()))
            .collect(Collectors.toList());

        // match each manual entry against all unmatched garmin entries of same type
        for (ManualEntry manualEntry : unmatchedManualEntries)
        {
            List<Long> garminIds = removeByType(unmatchedGarminEntries, manualEntry.getType());
            Workout workout = create(manualEntry, garminIds);
            results.add(workout);
        }

        // match existing workouts against remaining unmatched garmin entries
        for (Workout workout : workouts)
        {
            if (workout.getGarminIds().isEmpty() && ! unmatchedGarminEntries.isEmpty())
            {
                //check for dup matches from previous
                List<Long> garminIds = removeByType(unmatchedGarminEntries, workout.getType());
                if (! garminIds.isEmpty()) {
                    workout.setGarminIds(garminIds);
                    results.add(workout);
                }
            }
        }

        return results;
    }

    private static <T> Collection<T>  nullToEmpty(Collection<T> input)
    {
        return input != null ? input : Collections.emptyList();
    }

    private static Workout create(ManualEntry entry, List<Long> garminIds)
    {
        return new WorkoutBuilder()
            .setType(entry.getType())
            .setDuration(entry.getDuration())
            .setDate(entry.getDate())
            .setEntryTime(entry.getEntryTime())
            .setRating(entry.getRating())
            .setComment(entry.getComment())
            .setManualId(entry.getId())
            .setGarminIds(garminIds)
            .build();
    }

    private List<Long> removeByType(Collection<GarminEntry> entries, String manualType)
    {
        List<Long> results = new ArrayList<>(entries.size());
        Iterator<GarminEntry> iter = entries.iterator();
        while (iter.hasNext()) {
            GarminEntry entry = iter.next();
            if (matchesType(entry, manualType)) {
                results.add(entry.getId());
                iter.remove();
            }
        }
        return results;
    }

    private boolean matchesType(GarminEntry garminEntry, String manualType) {
        String garminType = garminEntry.getActivity().getType();
        return configuration.getTypeMapping().getOrDefault(garminType, garminType).equalsIgnoreCase(manualType);
    }
}
