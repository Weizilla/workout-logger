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

import static org.assertj.core.api.Assertions.assertThat;
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
    private String comment;
    private long garminId;
    private UUID manualId;

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
        id = UUID.randomUUID();
        entryTime = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        comment = "COMMENT";
        garminId = 123;
        manualId = UUID.randomUUID();
        workout = new Workout(id, type, duration, date, entryTime, comment, garminId, manualId);
    }

    @Test
    public void getsAllWorkout() throws Exception
    {
        when(workoutLogger.getAllWorkouts()).thenReturn(Lists.newArrayList(workout));

        mockMvc.perform(get("/api/workouts"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(WebTestUtils.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is(id.toString())))
            .andExpect(jsonPath("$[0].type", is(type)))
            .andExpect(jsonPath("$[0].duration", is(duration.toString())))
            .andExpect(jsonPath("$[0].date", is(dateString)))
            .andExpect(jsonPath("$[0].entryTime", is((int) entryTime.getEpochSecond())))
            .andExpect(jsonPath("$[0].comment", is(comment)))
            .andExpect(jsonPath("$[0].garminId", is((int) garminId)))
            .andExpect(jsonPath("$[0].manualId", is(manualId.toString())));

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
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getType()).isEqualTo(type);
        assertThat(actual.getDuration()).isEqualTo(duration);
        assertThat(actual.getEntryTime().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(entryTime);
        assertThat(actual.getDate()).isEqualTo(date);
        assertThat(actual.getComment()).isEqualTo(comment);
        assertThat(actual.getGarminId()).isPresent().contains(garminId);
        assertThat(actual.getManualId()).isPresent().contains(manualId);
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
            new Workout(id, type, duration, date, entryTime, comment, garminId, manualId),
            new Workout(id, type, duration, date, entryTime, comment, garminId, manualId),
            new Workout(id, type, duration, date, entryTime, comment, garminId, manualId));

        when(workoutLogger.getForDate(date)).thenReturn(workouts);

        mockMvc.perform(get("/api/workouts/dates/" + dateString))
            .andExpect(status().isOk())
            .andExpect(content().contentType(WebTestUtils.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].id", is(workouts.get(0).getId().toString())))
            .andExpect(jsonPath("$[1].id", is(workouts.get(1).getId().toString())))
            .andExpect(jsonPath("$[2].id", is(workouts.get(2).getId().toString())));
    }

    @Test
    public void getsAllWorkoutTypes() throws Exception
    {
        Set<String> types = Sets.newHashSet("a", "b", "c");
        when(workoutLogger.getAllTypes()).thenReturn(types);

        mockMvc.perform(get("/api/workouts/types"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(WebTestUtils.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$", containsInAnyOrder("a", "b", "c")));
    }
}