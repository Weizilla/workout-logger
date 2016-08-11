package com.weizilla.workout.logger.entity;

import com.weizilla.garmin.entity.Activity;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class GarminEntry implements Entry<Long>
{
    private final Activity activity;
    private UUID manualId;

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

    public Optional<UUID> getManualId()
    {
        return Optional.ofNullable(manualId);
    }

    public void setManualId(UUID manualId)
    {
        this.manualId = manualId;
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
        return Objects.equals(activity, that.activity) &&
            Objects.equals(manualId, that.manualId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(activity, manualId);
    }
}
