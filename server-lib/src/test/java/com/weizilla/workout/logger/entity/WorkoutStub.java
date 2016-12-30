package com.weizilla.workout.logger.entity;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class WorkoutStub
{
    public static List<Workout> createList()
    {
        return Collections.singletonList(create());
    }

    public static Workout create()
    {
        return createBase().build();
    }

    public static Workout create(String type)
    {
        return createBase()
            .setType(type)
            .build();
    }

    public static Workout create(UUID id)
    {
        return createBase()
            .setId(id)
            .build();
    }

    public static Workout create(LocalDate date)
    {
        return createBase()
            .setDate(date)
            .build();
    }

    public static Workout createWithManualId(UUID id)
    {
        return createBase()
            .setManualId(id)
            .build();
    }

    private static WorkoutBuilder createBase()
    {
        return new WorkoutBuilder()
            .setComment("COMMENT")
            .setDate(LocalDate.now())
            .setDuration(Duration.ofHours(1))
            .setRating(5)
            .setEntryTime(Instant.now())
            .setType("TYPE")
            .setManualId(UUID.randomUUID())
            .setGarminId((long) new Random().nextInt(100));
    }
}