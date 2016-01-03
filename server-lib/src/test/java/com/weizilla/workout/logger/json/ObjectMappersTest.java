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

import static com.weizilla.workout.logger.test.TestUtils.assertPrivateConstructor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ObjectMappersTest
{
    private LocalDate date;
    private Duration duration;
    private String type;
    private UUID id;
    private Instant entryTime;

    @Before
    public void setUp() throws Exception
    {
        date = LocalDate.of(2015, Month.NOVEMBER, 21);
        duration = Duration.ofHours(1);
        type = "TYPE";
        id = UUID.fromString("e487cc32-c5d9-417a-b0df-9aa0eb9154c3");
        entryTime = Instant.ofEpochSecond(1448146540);
    }

    @Test
    public void privateConstructor() throws Exception
    {
        assertPrivateConstructor(ObjectMappers.class);
    }

    @Test
    public void serializesWorkout() throws Exception
    {
        Workout workout = new Workout(id, type, duration, date, entryTime);
        String expected = TestUtils.readFile("workout-obj-mapper-test.json");
        String actual = ObjectMappers.OBJECT_MAPPER.writeValueAsString(workout);
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void deserializeWorkout() throws Exception
    {
        String input = TestUtils.readFile("workout-obj-mapper-test.json");
        Workout actual = ObjectMappers.OBJECT_MAPPER.readValue(input, Workout.class);
        assertThat(actual.getId(), is(id));
        assertThat(actual.getType(), is(type));
        assertThat(actual.getDuration(), is(duration));
        assertThat(actual.getDate(), is(date));
        assertThat(actual.getEntryTime(), is(entryTime));
    }
}