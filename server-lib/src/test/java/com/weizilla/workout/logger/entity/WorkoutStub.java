package com.weizilla.workout.logger.entity;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class WorkoutStub
{
    public static List<Workout> createList()
    {
        return Collections.singletonList(create());
    }

    public static Workout create()
    {
        return create("WORKOUT");
    }

    public static Workout create(String type)
    {
        return new WorkoutBuilder()
            .setComment("COMMENT")
            .setDate(LocalDate.now())
            .setDuration(Duration.ofHours(1))
            .setEntryTime(Instant.now())
            .setState(WorkoutState.MANUAL)
            .setType(type)
            .build();
    }
}