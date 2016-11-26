package com.weizilla.workout.logger.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.SECONDS;

public class ManualEntry implements Entry<UUID>
{
    @Id
    private final UUID id;
    private final String type;
    private final Duration duration;
    private final LocalDate date;
    private final Instant entryTime;
    private final String comment;

    public ManualEntry(String type, Duration duration)
    {
        this(null, type, duration, null, null, null);
    }

    @JsonCreator
    @PersistenceConstructor
    public ManualEntry(
        @JsonProperty("id") UUID id,
        @JsonProperty(value = "type", required = true) String type,
        @JsonProperty(value = "duration", required = true) Duration duration,
        @JsonProperty("date") LocalDate date,
        @JsonProperty("entryTime") Instant entryTime,
        @JsonProperty("comment") String comment)
    {
        this.id = id != null ? id : UUID.randomUUID();
        this.type = type.toLowerCase();
        this.duration = duration;
        this.date = date != null ? date : LocalDate.now();
        this.comment = comment == null || comment.trim().isEmpty() ? null : comment.trim();

        Instant now = entryTime != null ? entryTime : Instant.now();
        this.entryTime = now.truncatedTo(SECONDS);
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

    @Override
    public String toString()
    {
        return "ManualEntry{" +
            "id=" + id +
            ", type='" + type + '\'' +
            ", duration=" + duration +
            ", date=" + date +
            ", entryTime=" + entryTime +
            ", comment='" + comment + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        ManualEntry that = (ManualEntry) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(duration, that.duration) &&
            Objects.equals(date, that.date) &&
            Objects.equals(entryTime, that.entryTime) &&
            Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, type, duration, date, entryTime, comment);
    }
}
