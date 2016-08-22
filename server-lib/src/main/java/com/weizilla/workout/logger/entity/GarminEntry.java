package com.weizilla.workout.logger.entity;

import com.weizilla.garmin.entity.Activity;

import java.time.LocalDate;
import java.util.Objects;

public class GarminEntry implements Entry<Long>
{
    private final Activity activity;

    public GarminEntry(Activity activity)
    {
        this.activity = activity;
    }

    public Activity getActivity()
    {
        return activity;
    }

    @Override
    public Long getId()
    {
        return activity.getId();
    }

    @Override
    public LocalDate getDate()
    {
        return activity.getStart().toLocalDate();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        GarminEntry that = (GarminEntry) o;
        return Objects.equals(activity, that.activity);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(activity);
    }
}
