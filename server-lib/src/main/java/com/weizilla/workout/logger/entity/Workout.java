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
        @JsonProperty("type") String type,
        @JsonProperty("duration") Duration duration,
        @JsonProperty("date") LocalDate date,
        @JsonProperty("entryTime") Instant entryTime,
        @JsonProperty("comment") String comment,
        @JsonProperty("manualId") UUID manualId,
        @JsonProperty("garminId") Long garminId)
    {
        this.id = id;
        this.type = type;
        this.duration = duration;
        this.date = date;
        this.entryTime = entryTime;
        this.comment = comment;
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

    public UUID getManualId()
    {
        return manualId;
    }

    public Optional<Long> getGarminId()
    {
        return Optional.ofNullable(garminId);
    }

    public void setGarminId(long garminId)
    {
        this.garminId = garminId;
    }

    public boolean isMatched()
    {
        return garminId != null;
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
            ", manualId=" + manualId +
            ", garminId=" + garminId +
            '}';
    }
}
