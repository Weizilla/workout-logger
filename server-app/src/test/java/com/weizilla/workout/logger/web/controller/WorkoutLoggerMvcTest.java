package com.weizilla.workout.logger.web.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.weizilla.garmin.entity.Activity;
import com.weizilla.workout.logger.WorkoutLogger;
import com.weizilla.workout.logger.git.GitConfiguration;
import com.weizilla.workout.logger.entity.Export;
import com.weizilla.workout.logger.entity.GarminEntry;
import com.weizilla.workout.logger.entity.ManualEntry;
import com.weizilla.workout.logger.entity.ManualEntryStub;
import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.entity.WorkoutBuilder;
import com.weizilla.workout.logger.garmin.ActivityStub;
import com.weizilla.workout.logger.garmin.GarminEntryStub;
import com.weizilla.workout.logger.json.ObjectMappers;
import com.weizilla.workout.logger.web.WebTestUtils;
import com.weizilla.workout.logger.web.converter.WorkoutJsonConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//TODO not needed with Workout Logger Integration Tests providing better testing setup?
@RunWith(MockitoJUnitRunner.class)
public class WorkoutLoggerMvcTest
{
    @Mock
    private WorkoutLogger workoutLogger;
    @Captor
    private ArgumentCaptor<ManualEntry> manualEntryCaptor;
    private MockMvc mockMvc;
    private Workout workout;
    private String type;
    private Instant entryTime;
    private Duration duration;
    private LocalDate date;
    private int rating;
    private String dateString;
    private UUID id;
    private String comment;
    private long garminId;
    private UUID manualId;
    private ManualEntry manualEntry;
    private GarminEntry garminEntry;
    private GitConfiguration gitConfiguration;
    private String commitIdAbbrev;
    private String buildTime;

    @Before
    public void setUp() throws Exception
    {
        commitIdAbbrev = "COMMIT ID ABBREV";
        buildTime = "BUILD TIME";
        gitConfiguration = new GitConfiguration(commitIdAbbrev, buildTime);

        WorkoutLoggerController controller =
            new WorkoutLoggerController(workoutLogger, gitConfiguration);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setMessageConverters(new WorkoutJsonConverter())
            .build();

        type = "TYPE";
        duration = Duration.ofHours(10);
        date = LocalDate.now();
        dateString = date.toString();
        rating = 5;
        id = UUID.randomUUID();
        entryTime = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        comment = "COMMENT";
        garminId = 123;
        manualId = UUID.randomUUID();
        workout = new WorkoutBuilder()
            .setId(id)
            .setType(type)
            .setDuration(duration)
            .setRating(rating)
            .setDate(date)
            .setEntryTime(entryTime)
            .setComment(comment)
            .setGarminId(garminId)
            .setManualId(manualId)
            .build();
        manualEntry = ManualEntryStub.create();
        garminEntry = GarminEntryStub.create();
    }

    @Test
    public void addsManualEntry() throws Exception
    {
        String json = ObjectMappers.OBJECT_MAPPER.writeValueAsString(manualEntry);

        MockHttpServletRequestBuilder post = post("/api/entry")
            .contentType(WebTestUtils.APPLICATION_JSON_UTF8)
            .content(json);
        mockMvc.perform(post)
            .andExpect(status().isOk());

        verify(workoutLogger).addEntry(manualEntryCaptor.capture());

        ManualEntry actual = manualEntryCaptor.getValue();
        assertThat(actual).isEqualTo(manualEntry);
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
            .andExpect(jsonPath("$[0].type", is(type.toLowerCase())))
            .andExpect(jsonPath("$[0].matched", is(true)))
            .andExpect(jsonPath("$[0].duration", is(duration.toString())))
            .andExpect(jsonPath("$[0].rating", is(rating)))
            .andExpect(jsonPath("$[0].date", is(dateString)))
            .andExpect(jsonPath("$[0].entryTime", is((int) entryTime.getEpochSecond())))
            .andExpect(jsonPath("$[0].comment", is(comment)))
            .andExpect(jsonPath("$[0].garminIds[0]", is((int) garminId)))
            .andExpect(jsonPath("$[0].manualId", is(manualId.toString())));
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
        List<Workout> workouts = Lists.newArrayList(workout, workout, workout);

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

    @Test
    public void getGarminEntries() throws Exception
    {
        List<GarminEntry> entries = Collections.singletonList(garminEntry);
        when(workoutLogger.getGarminEntries()).thenReturn(entries);

        mockMvc.perform(get("/api/garmin/entry"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(WebTestUtils.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is(garminEntry.getId())))
            .andExpect(jsonPath("$[0].date", is(garminEntry.getDate().toString())))
            .andExpect(jsonPath("$[0].activity.id", is(garminEntry.getId())))
            .andExpect(jsonPath("$[0].activity.type", is(garminEntry.getActivity().getType())))
            .andExpect(jsonPath("$[0].activity.duration", is(garminEntry.getActivity().getDuration().toString())))
            .andExpect(jsonPath("$[0].activity.start", is(garminEntry.getActivity().getStart().toString())))
            .andExpect(jsonPath("$[0].activity.distance", is(garminEntry.getActivity().getDistance())));
    }

    @Test
    public void refreshGarminEntries() throws Exception
    {
        int result = 10;
        when(workoutLogger.refreshGarminEntries()).thenReturn(result);

        mockMvc.perform(get("/api/garmin/refresh"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(WebTestUtils.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.downloaded", is(result)));
    }

    @Test
    public void addGarminActivity() throws Exception
    {
        List<Activity> activities = ActivityStub.createList();

        String json = ObjectMappers.OBJECT_MAPPER.writeValueAsString(activities);

        MockHttpServletRequestBuilder post = post("/api/garmin/activity")
            .contentType(WebTestUtils.APPLICATION_JSON_UTF8)
            .content(json);
        mockMvc.perform(post)
            .andExpect(status().isOk());

        verify(workoutLogger).addGarminActivities(activities);
    }

    @Test
    public void exportsAll() throws Exception
    {
        Export export = new Export(Collections.singletonList(workout),
            Collections.singletonList(manualEntry), Collections.singletonList(garminEntry));
        when(workoutLogger.exportAll()).thenReturn(export);

        mockMvc.perform(get("/api/export"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(WebTestUtils.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.workouts", hasSize(1)))
            .andExpect(jsonPath("$.workouts[0].id", is(id.toString())))
            .andExpect(jsonPath("$.manualEntries", hasSize(1)))
            .andExpect(jsonPath("$.manualEntries[0].id", is(manualEntry.getId().toString())))
            .andExpect(jsonPath("$.garminEntries", hasSize(1)))
            .andExpect(jsonPath("$.garminEntries[0].id", is(garminEntry.getId())));
    }

    @Test
    public void deletesWorkout() throws Exception
    {
        mockMvc.perform(delete("/api/workouts/" + workout.getId()))
            .andExpect(status().isOk());

        verify(workoutLogger).deleteWorkout(workout.getId());
    }

    @Test
    public void getsGitConfiguration() throws Exception
    {
        mockMvc.perform(get("/api/git"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(WebTestUtils.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.commitIdAbbrev", is(commitIdAbbrev)))
            .andExpect(jsonPath("$.buildTime", is(buildTime)));
    }
}
