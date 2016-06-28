package com.weizilla.workout.logger.store;

import com.weizilla.garmin.entity.Activity;

import java.util.List;

public interface ActivityStore
{
    void put(Activity activity);
    List<Activity> getAll();
}