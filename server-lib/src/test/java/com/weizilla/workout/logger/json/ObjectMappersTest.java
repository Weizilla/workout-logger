package com.weizilla.workout.logger.json;

import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.entity.WorkoutBuilder;
import com.weizilla.workout.logger.entity.WorkoutState;
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
import static org.assertj.core.api.Assertions.assertThat;

public class ObjectMappersTest
{
    private LocalDate date;
    private Duration duration;
    private WorkoutState state;
    private String type;
    private UUID id;
    private Instant entryTime;
    private String comment;
    private long garminId;
    private UUID manualId;

    @Before
    public void setUp() throws Exception
    {
        date = LocalDate.of(2015, Month.NOVEMBER, 21);
        duration = Duration.ofHours(1);
        type = "TYPE";
        state = WorkoutState.MANUAL;
        id = UUID.fromString("e487cc32-c5d9-417a-b0df-9aa0eb9154c3");
        entryTime = Instant.ofEpochSecond(1448146540);
        comment = "COMMENT";
        garminId = 1234;
        manualId = UUID.fromString("7dda770f-d2f4-4331-b95c-ad70db2cf0e1");
    }

    @Test
    public void privateConstructor() throws Exception
    {
        assertPrivateConstructor(ObjectMappers.class);
    }

    @Test
    public void serializesWorkout() throws Exception
    {
        Workout workout = new WorkoutBuilder()
            .setId(id)
            .setType(type)
            .setState(state)
            .setDuration(duration)
            .setDate(date)
            .setEntryTime(entryTime)
            .setComment(comment)
            .setGarminId(garminId)
            .setManualId(manualId)
            .build();
        String expected = TestUtils.readFile("workout-obj-mapper-test.json");
        String actual = ObjectMappers.OBJECT_MAPPER.writeValueAsString(workout);
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void deserializeWorkout() throws Exception
    {
        String input = TestUtils.readFile("workout-obj-mapper-test.json");
        Workout actual = ObjectMappers.OBJECT_MAPPER.readValue(input, Workout.class);
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getType()).isEqualTo(type);
        assertThat(actual.getState()).isEqualTo(state);
        assertThat(actual.getDuration()).isEqualTo(duration);
        assertThat(actual.getDate()).isEqualTo(date);
        assertThat(actual.getEntryTime()).isEqualTo(entryTime);
        assertThat(actual.getComment()).isEqualTo(comment);
        assertThat(actual.getGarminId()).isPresent().contains(garminId);
        assertThat(actual.getManualId()).isPresent().contains(manualId);
    }
}