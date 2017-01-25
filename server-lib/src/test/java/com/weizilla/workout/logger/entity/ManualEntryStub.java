package com.weizilla.workout.logger.entity;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ManualEntryStub
{
    public static ManualEntry create()
    {
        return new ManualEntry(UUID.randomUUID(), "MANUAL TYPE", 1, Duration.ofDays(1), LocalDate.now(),
            Instant.now(), "COMMENT", UUID.randomUUID());
    }

    public static List<ManualEntry> createList()
    {
        return Collections.singletonList(create());
    }
}