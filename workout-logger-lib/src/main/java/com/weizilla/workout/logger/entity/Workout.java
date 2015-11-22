package com.weizilla.workout.logger.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;

public class Workout
{
    private final String type;
    private final Duration duration;
    private final LocalDate date;
    private final Instant entryTime;

    public Workout(String type, Duration duration, LocalDate date)
    {
        this(type, duration, date, Instant.now());
    }

    @JsonCreator
    public Workout(
        @JsonProperty("type") String type,
        @JsonProperty("duration") Duration duration,
        @JsonProperty("date") LocalDate date,
        @JsonProperty("entryTime") Instant entryTime)
    {
        this.type = type;
        this.duration = duration;
        this.date = date;
        this.entryTime = entryTime;
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
