package com.weizilla.workout.logger.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class Workout
{
    @Id
    private final UUID id;
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

    @PersistenceConstructor
    public Workout(
        UUID id,
        String type,
        Duration duration,
        LocalDate date,
        Instant entryTime)
    {
        this.id = id;
        this.type = type;
        this.duration = duration;
        this.date = date;
        this.entryTime = entryTime;
    }

    public UUID getId()
    {
        return id;
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

    @Override
    public String toString()
    {
        return "Workout{" +
            "id=" + id +
            ", type='" + type + '\'' +
            ", duration=" + duration +
            ", date=" + date +
            ", entryTime=" + entryTime +
            '}';
    }
}
