package com.weizilla.workout.logger.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class Workout
{
    private final UUID uuid;
    private final String type;
    private final Duration duration;
    private final LocalDate date;
    private final Instant entryTime;

    @JsonCreator
    public Workout(
        @JsonProperty("type") String type,
        @JsonProperty("duration") Duration duration,
        @JsonProperty("date") LocalDate date)
    {
        this(UUID.randomUUID(), type, duration, date, Instant.now());
    }

    public Workout(
        UUID uuid,
        String type,
        Duration duration,
        LocalDate date,
        Instant entryTime)
    {
        this.uuid = uuid;
        this.type = type;
        this.duration = duration;
        this.date = date;
        this.entryTime = entryTime;
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public String getType()
    {
        return type;
    }

    public Duration getDuration()
    {
        return duration;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public Instant getEntryTime()
    {
        return entryTime;
    }
}
