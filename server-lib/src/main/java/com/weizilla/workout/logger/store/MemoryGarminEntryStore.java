package com.weizilla.workout.logger.store;

import com.weizilla.garmin.entity.Activity;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Profile("memory")
@Component
public class MemoryGarminEntryStore implements GarminEntryStore
{
    private final Map<Long, Activity> activities = new TreeMap<>();

    @Override
    public void put(Activity activity)
    {
        activities.put(activity.getId(), activity);
    }

    @Override
    public List<Activity> getAll()
    {
        return new ArrayList<>(activities.values());
    }

    @Override
    public void deleteAll()
    {
        activities.clear();
    }
}