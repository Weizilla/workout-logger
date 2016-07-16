package com.weizilla.workout.logger.garmin;

import com.weizilla.garmin.entity.Activity;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public class ActivityStub
{
    public static Activity create()
    {
        return new Activity(new Random().nextInt(), "TYPE", Duration.ofDays(1), Instant.now(), 12.3);
    }
}
