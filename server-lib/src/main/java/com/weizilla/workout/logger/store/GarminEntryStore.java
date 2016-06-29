package com.weizilla.workout.logger.store;

import com.weizilla.garmin.entity.Activity;

import java.util.List;

public interface GarminEntryStore
{
    void put(Activity activity);
    List<Activity> getAll();
}