package com.weizilla.workout.logger.web.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
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
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
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
    private String dateString;
    private UUID id;

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
        dateString = date.toString();
        workout = new Workout(type, duration);
        id = workout.getId();
        entryTime = workout.getEntryTime().truncatedTo(ChronoUnit.SECONDS);
    }

    @Test
    public void getsAllWorkout() throws Exception
    {
        when(workoutLogger.getAll()).thenReturn(Lists.newArrayList(workout));

        mockMvc.perform(get("/api/workouts"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(WebTestUtils.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is(id.toString())))
            .andExpect(jsonPath("$[0].type", is(type)))
            .andExpect(jsonPath("$[0].duration", is(duration.toString())))
            .andExpect(jsonPath("$[0].date", is(dateString)))
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
        verify(workoutLogger).put(captor.capture());

        Workout actual = captor.getValue();
        assertThat(actual.getId(), is(id));
        assertThat(actual.getType(), is(type));
        assertThat(actual.getDuration(), is(duration));
        assertThat(actual.getEntryTime(), is(entryTime));
        assertThat(actual.getDate(), is(date));
    }

    @Test
    public void getsAllWorkoutDates() throws Exception
    {
        Set<LocalDate> dates = Sets.newHashSet(
            LocalDate.of(2016, 1, 1),
            LocalDate.of(2016, 1, 2),
            LocalDate.of(2016, 1, 3));

        when(workoutLogger.getAllDates()).thenReturn(dates);

        mockMvc.perform(get("/api/workouts/dates"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(WebTestUtils.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$", containsInAnyOrder("2016-01-01", "2016-01-02", "2016-01-03")));
    }

    @Test
    public void getWorkoutsByDate() throws Exception
    {
        List<Workout> workouts = Lists.newArrayList(
            new Workout(type, duration),
            new Workout(type, duration),
            new Workout(type, duration));

        when(workoutLogger.getForDate(date)).thenReturn(workouts);

        mockMvc.perform(get("/api/workouts/dates/" + dateString))
            .andExpect(status().isOk())
            .andExpect(content().contentType(WebTestUtils.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].id", is(workouts.get(0).getId().toString())))
            .andExpect(jsonPath("$[1].id", is(workouts.get(1).getId().toString())))
            .andExpect(jsonPath("$[2].id", is(workouts.get(2).getId().toString())));
    }
}