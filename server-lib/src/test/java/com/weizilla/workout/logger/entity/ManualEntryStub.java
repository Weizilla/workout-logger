package com.weizilla.workout.logger.entity;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class ManualEntryStub
{
    public static ManualEntry create()
    {
        return new ManualEntry(UUID.randomUUID(), "TYPE", Duration.ofDays(1), LocalDate.now(), Instant.now(),
            "COMMENT");
    }
}