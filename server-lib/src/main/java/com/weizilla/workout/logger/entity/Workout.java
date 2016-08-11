package com.weizilla.workout.logger.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class Workout implements Entry<UUID>
{
    @Id
    private final UUID id;
    private final String type;
    private final WorkoutState state;
    private final Duration duration;
    private final LocalDate date;
    private final Instant entryTime;
    private final String comment;
    private Long garminId;
    private UUID manualId;

    @JsonCreator
    @PersistenceConstructor
    public Workout(
        @JsonProperty("id") UUID id,
        @JsonProperty(value = "type", required = true) String type,
        @JsonProperty(value = "state", required = true) WorkoutState state,
        @JsonProperty(value = "duration", required = true) Duration duration,
        @JsonProperty("date") LocalDate date,
        @JsonProperty("entryTime") Instant entryTime,
        @JsonProperty("comment") String comment,
        @JsonProperty("garminId") Long garminId,
        @JsonProperty("manualId") UUID manualId)
    {
        this.id = id != null ? id : UUID.randomUUID();
        this.type = type;
        this.state = state;
        this.duration = duration;
        this.date = date != null ? date : LocalDate.now();
        this.entryTime = entryTime != null ? entryTime : Instant.now();
        this.comment = comment == null || comment.trim().isEmpty() ? null : comment.trim();
        this.garminId = garminId;
        this.manualId = manualId;
    }

    @Override
    public UUID getId()
    {
        return id;
    }

    public String getType()
    {
        return type;
    }

    public WorkoutState getState()
    {
        return state;
    }

    public Duration getDuration()
    {
        return duration;
    }

    @Override
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

    public Optional<Long> getGarminId()
    {
        return Optional.ofNullable(garminId);
    }

    public void setGarminId(long garminId)
    {
        this.garminId = garminId;
    }

    public Optional<UUID> getManualId()
    {
        return Optional.ofNullable(manualId);
    }

    public void setManualId(UUID manualId)
    {
        this.manualId = manualId;
    }

    @Override
    public String toString()
    {
        return "Workout{" +
            "id=" + id +
            ", type='" + type + '\'' +
            ", state=" + state +
            ", duration=" + duration +
            ", date=" + date +
            ", entryTime=" + entryTime +
            ", comment=" + comment +
            ", garminId=" + garminId +
            ", manualId=" + manualId +
            '}';
    }
}
