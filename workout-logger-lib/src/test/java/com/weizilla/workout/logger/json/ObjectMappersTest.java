package com.weizilla.workout.logger.json;

import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.test.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ObjectMappersTest
{
    private Workout workout;
    private String json;

    @Before
    public void setUp() throws Exception
    {
        LocalDate date = LocalDate.of(2015, Month.NOVEMBER, 21);
        workout = new Workout("TYPE", Duration.ofHours(1), date, Instant.ofEpochSecond(1448146540));
        json = TestUtils.readFile("workout-json-test.json");
    }

    @Test
    public void serializesWorkout() throws Exception
    {
        String actual = ObjectMappers.OBJECT_MAPPER.writeValueAsString(workout);
        JSONAssert.assertEquals(json, actual, true);
    }

    @Test
    public void deserializeWorkout() throws Exception
    {
        Workout actual = ObjectMappers.OBJECT_MAPPER.readValue(json, Workout.class);
        assertThat(actual.getType(), is(workout.getType()));
        assertThat(actual.getDuration(), is(workout.getDuration()));
        assertThat(actual.getDate(), is(workout.getDate()));
        assertThat(actual.getEntryTime(), is(workout.getEntryTime()));
    }
}