package com.weizilla.workout.logger.web.controller;

import com.google.common.collect.Lists;
import com.weizilla.workout.logger.WorkoutLogger;
import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.web.WebTestUtils;
import com.weizilla.workout.logger.web.converter.WorkoutJsonConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Duration;
import java.time.LocalDate;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private int entryTime;
    private Duration duration;
    private String durationStr;
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
        entryTime = (int) workout.getEntryTime().getEpochSecond();
    }

    @Test
    public void getsAllWorkout() throws Exception
    {
        when(workoutLogger.getWorkouts()).thenReturn(Lists.newArrayList(workout));

        mockMvc.perform(get("/api/workouts"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(WebTestUtils.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].type", equalTo(type)))
            .andExpect(jsonPath("$[0].duration", equalTo(duration.toString())))
            .andExpect(jsonPath("$[0].date", equalTo(date.toString())))
            .andExpect(jsonPath("$[0].entryTime", equalTo(entryTime)));
    }
}