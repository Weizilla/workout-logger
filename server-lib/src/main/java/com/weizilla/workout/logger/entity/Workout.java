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
    private final String comment;

    public Workout(String type, Duration duration)
    {
        this(null, type, duration, null, null, null);
    }

    @JsonCreator
    @PersistenceConstructor
    public Workout(
        @JsonProperty("id") UUID id,
        @JsonProperty(value = "type", required = true) String type,
        @JsonProperty(value = "duration", required = true) Duration duration,
        @JsonProperty("date") LocalDate date,
        @JsonProperty("entryTime") Instant entryTime,
        @JsonProperty("comment") String comment)
    {
        this.id = id != null ? id : UUID.randomUUID();
        this.type = type;
        this.duration = duration;
        this.date = date != null ? date : LocalDate.now();
        this.entryTime = entryTime != null ? entryTime : Instant.now();
        this.comment = comment == null || comment.trim().isEmpty() ? null : comment.trim();
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

    public String getComment()
    {
        return comment;
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
            ", comment=" + comment +
            '}';
    }
}
