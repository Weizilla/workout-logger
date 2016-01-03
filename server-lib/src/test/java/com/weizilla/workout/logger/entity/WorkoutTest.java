package com.weizilla.workout.logger.entity;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.Is.is;

public class WorkoutTest
{
    @Test
    public void populatesDefaultValues() throws Exception
    {
        Workout workout = new Workout("TYPE", Duration.ofHours(1));
        assertThat(workout.getId(), is(notNullValue()));
        assertThat(workout.getDate(), is(LocalDate.now()));
        assertThat(workout.getEntryTime(), is(notNullValue()));
    }

    @Test
    public void populatesDefaultValuesGivenNull() throws Exception
    {
        Workout workout = new Workout(null, "TYPE", Duration.ofHours(1), null, null);
        assertThat(workout.getId(), is(notNullValue()));
        assertThat(workout.getDate(), is(LocalDate.now()));
        assertThat(workout.getEntryTime(), is(notNullValue()));
    }
}