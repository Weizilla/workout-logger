package com.weizilla.workout.logger.entity;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkoutTest
{
    @Test
    public void populatesDefaultValues() throws Exception
    {
        Workout workout = new Workout("TYPE", Duration.ofHours(1));
        assertThat(workout.getId()).isNotNull();
        assertThat(workout.getDate()).isEqualTo(LocalDate.now());
        assertThat(workout.getEntryTime()).isNotNull();
    }

    @Test
    public void populatesDefaultValuesGivenNull() throws Exception
    {
        Workout workout = new Workout(null, "TYPE", Duration.ofHours(1), null, null, null);
        assertThat(workout.getId()).isNotNull();
        assertThat(workout.getDate()).isEqualTo(LocalDate.now());
        assertThat(workout.getEntryTime()).isNotNull();
    }

    @Test
    public void returnsStringWithValues() throws Exception
    {
        Workout workout = new Workout("TYPE", Duration.ofHours(1));
        assertThat(workout.toString()).isNotNull();
    }

    @Test
    public void stripsComment() throws Exception
    {
        String comment = " comment ";
        Workout workout = new Workout(null, "TYPE", Duration.ofHours(1), null, null, comment);
        String actual = workout.getComment();
        assertThat(actual).isEqualTo(comment.trim());
    }

    @Test
    public void storesNullIfCommentIsEmptyString() throws Exception
    {
        String comment = "    ";
        Workout workout = new Workout(null, "TYPE", Duration.ofHours(1), null, null, comment);
        String actual = workout.getComment();
        assertThat(actual).isNull();
    }
}