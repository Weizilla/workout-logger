package com.weizilla.workout.logger.garmin;

import com.weizilla.workout.logger.store.GarminEntryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GarminManager
{
    private final GarminEntryStore store;

    @Autowired
    public GarminManager(GarminEntryStore store)
    {
        this.store = store;
    }
}