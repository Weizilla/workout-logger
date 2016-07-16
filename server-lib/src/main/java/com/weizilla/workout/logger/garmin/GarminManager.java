package com.weizilla.workout.logger.garmin;

import com.weizilla.garmin.ActivityDownloader;
import com.weizilla.garmin.entity.Activity;
import com.weizilla.workout.logger.store.GarminEntryStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public List<Activity> getAllEntries()
    {
        return store.getAll();
    }

    public List<Activity> refreshEntries()
    {
        try
        {
            List<Activity> downloaded = activityDownloader.download();
            Set<Long> existingIds = store.getIds();
            List<Activity> newActivities = downloaded.stream()
                .filter(a -> ! existingIds.contains(a.getId()))
                .collect(Collectors.toList());
            store.putAll(newActivities);
            logger.info("Downloaded {} activities of which {} were new", downloaded.size(), newActivities.size());
            return newActivities;
        }
        catch (Exception e)
        {
            logger.error("Error downloading activities: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}