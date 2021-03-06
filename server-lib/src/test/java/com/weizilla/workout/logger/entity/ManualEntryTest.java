package com.weizilla.workout.logger.entity;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class ManualEntryTest
{
    @Test
    public void populatesDefaultValuesGivenNull() throws Exception
    {
        ManualEntry workout = new ManualEntry(null, "TYPE", 4, Duration.ofHours(1), null, null, null, null);
        assertThat(workout.getId()).isNotNull();
        assertThat(workout.getDate()).isEqualTo(LocalDate.now());
        assertThat(workout.getEntryTime()).isNotNull();
    }

    @Test
    public void stripsComment() throws Exception
    {
        String comment = " comment ";
        ManualEntry workout = new ManualEntry(null, "TYPE", 4, Duration.ofHours(1), null, null, comment, null);
        String actual = workout.getComment();
        assertThat(actual).isEqualTo(comment.trim());
    }

    @Test
    public void storesNullIfCommentIsEmptyString() throws Exception
    {
        String comment = "    ";
        ManualEntry workout = new ManualEntry(null, "TYPE", 4, Duration.ofHours(1), null, null, comment, null);
        String actual = workout.getComment();
        assertThat(actual).isNull();
    }
}