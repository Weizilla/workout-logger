package com.weizilla.workout.logger.garmin;

import com.weizilla.workout.logger.entity.GarminEntry;

import java.time.LocalDateTime;

public class GarminEntryStub
{
    public static GarminEntry create()
    {
        return new GarminEntry(ActivityStub.create());
    }

    public static GarminEntry create(LocalDateTime start)
    {
        return new GarminEntry(ActivityStub.create(start));
    }
}
