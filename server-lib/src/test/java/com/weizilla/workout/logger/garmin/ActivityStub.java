package com.weizilla.workout.logger.garmin;

import com.weizilla.garmin.entity.Activity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

public class ActivityStub
{
    public static Activity create()
    {
        return new Activity(new Random().nextLong(), "TYPE", Duration.ofDays(1), LocalDateTime.now(), 12.3);
    }

    public static Activity create(LocalDateTime start)
    {
        return new Activity(new Random().nextLong(), "TYPE", Duration.ofDays(1), start, 12.3);
    }

    public static Activity create(String type)
    {
        return new Activity(new Random().nextLong(), type, Duration.ofDays(1), LocalDateTime.now(), 12.3);
    }
}
