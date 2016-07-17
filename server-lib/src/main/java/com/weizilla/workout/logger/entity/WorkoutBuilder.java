package com.weizilla.workout.logger.entity;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class WorkoutBuilder
{
    private String type;
    private Duration duration;
    private UUID id;
    private LocalDate date;
    private Instant entryTime;
    private String comment;
    private Long garminId;
    private UUID manualId;

    public WorkoutBuilder setId(UUID id)
    {
        this.id = id;
        return this;
    }

    public WorkoutBuilder setType(String type)
    {
        this.type = type;
        return this;
    }

    public WorkoutBuilder setDuration(Duration duration)
    {
        this.duration = duration;
        return this;
    }

    public WorkoutBuilder setDate(LocalDate date)
    {
        this.date = date;
        return this;
    }

    public WorkoutBuilder setEntryTime(Instant entryTime)
    {
        this.entryTime = entryTime;
        return this;
    }

    public WorkoutBuilder setComment(String comment)
    {
        this.comment = comment;
        return this;
    }

    public WorkoutBuilder setGarminId(Long garminId)
    {
        this.garminId = garminId;
        return this;
    }

    public WorkoutBuilder setManualId(UUID manualId)
    {
        this.manualId = manualId;
        return this;
    }

    public Workout build()
    {
        id = id != null ? id : UUID.randomUUID();
        date = date != null ? date : LocalDate.now();
        entryTime = entryTime != null ? entryTime : Instant.now();
        comment = comment == null || comment.trim().isEmpty() ? null : comment.trim();
        return new Workout(id, type, duration, date, entryTime, comment, garminId, manualId);
    }
}
