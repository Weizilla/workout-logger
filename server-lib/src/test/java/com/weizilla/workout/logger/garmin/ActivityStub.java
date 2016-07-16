package com.weizilla.workout.logger.garmin;

import com.weizilla.garmin.entity.Activity;

import java.time.Duration;
import java.time.Instant;

public class ActivityStub
{
    public static Activity create()
    {
        return new Activity(1, "TYPE", Duration.ofDays(1), Instant.now(), 12.3);
    }
}
