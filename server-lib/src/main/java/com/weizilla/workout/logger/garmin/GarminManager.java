package com.weizilla.workout.logger.garmin;

import com.weizilla.garmin.ActivityDownloader;
import com.weizilla.garmin.entity.Activity;
import com.weizilla.workout.logger.entity.GarminEntry;
import com.weizilla.workout.logger.store.GarminEntryStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GarminManager
{
    private static final Logger logger = LoggerFactory.getLogger(GarminManager.class);
    private final GarminEntryStore store;
    private final ActivityDownloader activityDownloader;

    @Autowired
    public GarminManager(GarminEntryStore store, ActivityDownloader activityDownloader)
    {
        this.store = store;
        this.activityDownloader = activityDownloader;
    }

    public Collection<GarminEntry> getAllEntries()
    {
        return store.getAll();
    }

    public Collection<GarminEntry> refreshEntries()
    {
        try
        {
            List<Activity> downloaded = activityDownloader.download();
            return addActivities(downloaded);
        }
        catch (Exception e)
        {
            logger.error("Error downloading activities: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public Collection<GarminEntry> addActivities(Collection<Activity> activities)
    {
        try
        {
            Set<Long> existingIds = store.getIds();
            List<GarminEntry> newActivities = activities.stream()
                .filter(a -> ! existingIds.contains(a.getId()))
                .map(GarminEntry::new)
                .collect(Collectors.toList());
            store.putAll(newActivities);
            logger.info("Imported {} activities of which {} were new", activities.size(), newActivities.size());
            return newActivities;
        }
        catch (Exception e)
        {
            logger.error("Error importing activities: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}