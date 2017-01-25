package com.weizilla.workout.logger.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.SECONDS;

public class ManualEntry implements Entry<UUID>
{
    @Id
    private final UUID id;
    private final String type;
    private final Duration duration;
    private final int rating;
    private final LocalDate date;
    private final Instant entryTime;
    private final String comment;
    private UUID workoutId;

    @JsonCreator
    @PersistenceConstructor
    public ManualEntry(
        @JsonProperty("id") UUID id,
        @JsonProperty(value = "type", required = true) String type,
        @JsonProperty(value = "rating", required = true) int rating,
        @JsonProperty("duration") Duration duration,
        @JsonProperty("date") LocalDate date,
        @JsonProperty("entryTime") Instant entryTime,
        @JsonProperty("comment") String comment,
        @JsonProperty("workoutId") UUID workoutId)
    {
        this.id = id != null ? id : UUID.randomUUID();
        this.type = type.toLowerCase();
        this.duration = duration;
        this.rating = rating;
        this.date = date != null ? date : LocalDate.now();
        this.comment = comment == null || comment.trim().isEmpty() ? null : comment.trim();

        Instant now = entryTime != null ? entryTime : Instant.now();
        this.entryTime = now.truncatedTo(SECONDS);
        this.workoutId = workoutId;
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

    public int getRating()
    {
        return rating;
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

    public Optional<UUID> getWorkoutId()
    {
        return Optional.ofNullable(workoutId);
    }

    public void setWorkoutId(UUID workoutId)
    {
        this.workoutId = workoutId;
    }

    @Override
    public String toString() {
        return "ManualEntry{" +
            "id=" + id +
            ", type='" + type + '\'' +
            ", duration=" + duration +
            ", rating=" + rating +
            ", date=" + date +
            ", entryTime=" + entryTime +
            ", comment='" + comment + '\'' +
            ", workoutId=" + workoutId +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ManualEntry entry = (ManualEntry) o;
        return rating == entry.rating &&
            Objects.equals(id, entry.id) &&
            Objects.equals(type, entry.type) &&
            Objects.equals(duration, entry.duration) &&
            Objects.equals(date, entry.date) &&
            Objects.equals(entryTime, entry.entryTime) &&
            Objects.equals(comment, entry.comment) &&
            Objects.equals(workoutId, entry.workoutId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, duration, rating, date, entryTime, comment, workoutId);
    }
}
