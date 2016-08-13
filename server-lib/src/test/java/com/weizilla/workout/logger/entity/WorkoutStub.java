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
        return new WorkoutBuilder()
            .setComment("COMMENT")
            .setDate(LocalDate.now())
            .setDuration(Duration.ofHours(1))
            .setEntryTime(Instant.now())
            .setGarminId(new Random().nextLong())
            .setManualId(UUID.randomUUID())
            .setState(WorkoutState.MANUAL)
            .setType("WORKOUT")
            .build();
    }
}