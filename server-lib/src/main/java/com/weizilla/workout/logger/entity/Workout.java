package com.weizilla.workout.logger.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    private final int rating;
    private List<Long> garminIds;
    private UUID manualId;

    @JsonCreator
    @PersistenceConstructor
    public Workout(
        @JsonProperty("id") UUID id,
        @JsonProperty("type") String type,
        @JsonProperty("duration") Duration duration,
        @JsonProperty("rating") int rating,
        @JsonProperty("date") LocalDate date,
        @JsonProperty("entryTime") Instant entryTime,
        @JsonProperty("comment") String comment,
        @JsonProperty("manualId") UUID manualId,
        @JsonProperty("garminIds") List<Long> garminIds)
    {
        this.id = id;
        this.type = type.toLowerCase();
        this.duration = duration;
        this.rating = rating;
        this.date = date;
        this.entryTime = entryTime;
        this.comment = comment;
        this.manualId = manualId;
        this.garminIds = garminIds != null ? new ArrayList<>(garminIds) : Collections.emptyList();
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

    public int getRating()
    {
        return rating;
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

    public List<Long> getGarminIds()
    {
        return Collections.unmodifiableList(garminIds);
    }

    public void setGarminId(long garminIds)
    {
        this.garminIds = Collections.singletonList(garminIds);
    }

    public void setGarminIds(List<Long> garminIds)
    {
        this.garminIds = new ArrayList<>(garminIds);
    }

    public boolean isMatched()
    {
        return garminIds != null && ! garminIds.isEmpty();
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
            ", garminIds=" + garminIds +
            '}';
    }
}
