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

    public Workout(String type, Duration duration)
    {
        this(null, type, duration, null, null);
    }

    @JsonCreator
    @PersistenceConstructor
    public Workout(
        @JsonProperty(value = "id", required = false) UUID id,
        @JsonProperty("type") String type,
        @JsonProperty("duration") Duration duration,
        @JsonProperty(value = "date", required = false) LocalDate date,
        @JsonProperty(value = "entryTime", required = false) Instant entryTime)
    {
        this.id = id != null ? id : UUID.randomUUID();
        this.type = type;
        this.duration = duration;
        this.date = date != null ? date : LocalDate.now();
        this.entryTime = entryTime != null ? entryTime : Instant.now();
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
