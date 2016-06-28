package com.weizilla.workout.logger.activity;

import com.weizilla.workout.logger.store.ActivityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivityManager
{
    private final ActivityStore store;

    @Autowired
    public ActivityManager(ActivityStore store)
    {
        this.store = store;
    }
}