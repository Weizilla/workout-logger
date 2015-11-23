package com.weizilla.workout.logger.web.controller;

import com.google.common.collect.Lists;
import com.weizilla.workout.logger.WorkoutLogger;
import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.json.ObjectMappers;
import com.weizilla.workout.logger.web.WebTestUtils;
import com.weizilla.workout.logger.web.converter.WorkoutJsonConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class WorkoutLoggerControllerTest
{
    @Mock
    private WorkoutLogger workoutLogger;
    private MockMvc mockMvc;
    private Workout workout;
    private String type;
    private Instant entryTime;
    private Duration duration;
    private LocalDate date;

    @Before
    public void setUp() throws Exception
    {
        WorkoutLoggerController controller = new WorkoutLoggerController(workoutLogger);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setMessageConverters(new WorkoutJsonConverter())
            .build();

        type = "TYPE";
        duration = Duration.ofHours(10);
        date = LocalDate.now();
        workout = new Workout(type, duration, date);
        entryTime = workout.getEntryTime().truncatedTo(ChronoUnit.SECONDS);
    }

    @Test
    public void getsAllWorkout() throws Exception
    {
        when(workoutLogger.getWorkouts()).thenReturn(Lists.newArrayList(workout));

        mockMvc.perform(get("/api/workouts"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(WebTestUtils.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].type", is(type)))
            .andExpect(jsonPath("$[0].duration", is(duration.toString())))
            .andExpect(jsonPath("$[0].date", is(date.toString())))
            .andExpect(jsonPath("$[0].entryTime", is((int) entryTime.getEpochSecond())));
    }

    @Test
    public void addsWorkout() throws Exception
    {
        String json = ObjectMappers.OBJECT_MAPPER.writeValueAsString(workout);

        MockHttpServletRequestBuilder post = post("/api/workouts")
            .contentType(WebTestUtils.APPLICATION_JSON_UTF8)
            .content(json);
        mockMvc.perform(post)
            .andExpect(status().isOk());

        ArgumentCaptor<Workout> captor = ArgumentCaptor.forClass(Workout.class);
        verify(workoutLogger).store(captor.capture());

        Workout actual = captor.getValue();
        assertThat(actual.getType(), is(type));
        assertThat(actual.getDuration(), is(duration));
        assertThat(actual.getEntryTime(), is(entryTime));
        assertThat(actual.getDate(), is(date));
    }
}