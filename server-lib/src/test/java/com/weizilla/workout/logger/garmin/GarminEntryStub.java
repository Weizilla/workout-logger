package com.weizilla.workout.logger.garmin;

import com.weizilla.workout.logger.entity.GarminEntry;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

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

    public static List<GarminEntry> createList()
    {
        return Collections.singletonList(create());
    }
}
