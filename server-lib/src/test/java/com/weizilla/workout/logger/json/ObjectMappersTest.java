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
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class ObjectMappersTest
{
    private LocalDate date;
    private Duration duration;
    private String type;

    @Before
    public void setUp() throws Exception
    {
        date = LocalDate.of(2015, Month.NOVEMBER, 21);
        duration = Duration.ofHours(1);
        type = "TYPE";
    }

    @Test
    public void serializesWorkout() throws Exception
    {
        UUID uuid = UUID.fromString("e487cc32-c5d9-417a-b0df-9aa0eb9154c3");
        Workout workout = new Workout(uuid, type, duration, date, Instant.ofEpochSecond(1448146540));
        String expected = TestUtils.readFile("workout-serialize-test.json");
        String actual = ObjectMappers.OBJECT_MAPPER.writeValueAsString(workout);
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void deserializeWorkout() throws Exception
    {
        String input = TestUtils.readFile("workout-deserialize-test.json");
        Workout actual = ObjectMappers.OBJECT_MAPPER.readValue(input, Workout.class);
        assertThat(actual.getId(), is(notNullValue()));
        assertThat(actual.getType(), is(type));
        assertThat(actual.getDuration(), is(duration));
        assertThat(actual.getDate(), is(date));
        assertThat(actual.getEntryTime(), is(notNullValue()));
    }
}