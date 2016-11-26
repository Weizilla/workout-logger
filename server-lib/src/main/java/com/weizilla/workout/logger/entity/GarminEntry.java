package com.weizilla.workout.logger.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.weizilla.garmin.entity.Activity;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import java.time.LocalDate;
import java.util.Objects;

public class GarminEntry implements Entry<Long>
{
    @Id
    private final long id;
    private final Activity activity;

    public GarminEntry(Activity activity)
    {
        id = activity.getId();
        this.activity = activity;
    }

    @PersistenceConstructor
    @JsonCreator
    protected GarminEntry(@JsonProperty("id") Long id, @JsonProperty("activity") Activity activity)
    {
        this.id = id;
        this.activity = activity;
    }

    public Activity getActivity()
    {
        return activity;
    }

    @Override
    public Long getId()
    {
        return id;
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

    @Override
    public String toString()
    {
        return "GarminEntry{" +
            "activity=" + activity +
            '}';
    }
}
