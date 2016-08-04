package com.weizilla.workout.logger.store;

import com.weizilla.garmin.entity.Activity;

import java.util.Collections;
import java.util.List;

public class GarminEntryStoreStub implements GarminEntryStore
{
    private List<Activity> activities = Collections.emptyList();

    public void setActivities(List<Activity> activities)
    {
        this.activities = activities;
    }

    @Override
    public void put(Activity activity)
    {

    }

    @Override
    public List<Activity> getAll()
    {
        return activities;
    }

    @Override
    public void deleteAll()
    {

    }
}
