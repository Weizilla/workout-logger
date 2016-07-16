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
        //TODO unit test
        try
        {
            return activityDownloader.download();
        }
        catch (Exception e)
        {
            logger.error("Error downloading Garmin entries: {}", e.getMessage(), e);
        }
        return Collections.emptyList();
    }
}