package com.weizilla.workout.logger;

import com.mongodb.MongoClient;
import com.weizilla.workout.logger.entity.ManualEntry;
import com.weizilla.workout.logger.entity.Workout;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = {"dev", "memory", "server-app-test"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class WorkoutLoggerWorkoutsIntTest
{
    @MockBean
    private MongoClient mongoClient;

    @Autowired
    private TestRestTemplate template;

    private String type;
    private Instant entryTime;
    private Duration duration;
    private int rating;
    private LocalDate date;
    private String comment;
    private UUID manualId;
    private ManualEntry manualEntry;
    private UUID workoutId;

    @Before
    public void setUp() throws Exception
    {
        type = "TYPE";
        duration = Duration.ofHours(10);
        date = LocalDate.now();
        rating = 5;
        entryTime = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        comment = "COMMENT";
        manualId = UUID.randomUUID();
        workoutId = UUID.randomUUID();
        manualEntry = new ManualEntry(manualId, type, rating, duration, date, entryTime, comment, workoutId);
    }

    @Test
    public void templateIsWiredBySpring() throws Exception
    {
        assertThat(template).isNotNull();
    }

    @Test
    public void noActivitiesOnStartUp() throws Exception
    {
        Workout[] workouts = template.getForObject("/api/workouts", Workout[].class);
        assertThat(workouts).isNotNull();
        assertThat(workouts).isEmpty();
    }

    @Test
    public void addEntryReturnsNewWorkout() throws Exception
    {
        template.postForLocation("/api/entry", manualEntry);

        Workout[] workouts = template.getForObject("/api/workouts", Workout[].class);
        assertThat(workouts).isNotNull();
        assertThat(workouts).hasSize(1);

        Workout actual = workouts[0];
        assertThat(actual.getType()).isEqualTo(type.toLowerCase());
        assertThat(actual.getDuration()).isEqualTo(duration);
    }

    @Test
    public void returnsAllDatesForWorkouts() throws Exception
    {
        template.postForLocation("/api/entry", manualEntry);

        LocalDate[] actual = template.getForObject("/api/workouts/dates", LocalDate[].class);
        assertThat(actual).containsExactly(date);
    }

    @Test
    public void returnsAllWorkoutsForDate() throws Exception
    {
        template.postForLocation("/api/entry", manualEntry);

        Workout[] workouts = template.getForObject("/api/workouts/dates/" + date, Workout[].class);
        assertThat(workouts).isNotNull();
        assertThat(workouts).hasSize(1);

        Workout actual = workouts[0];
        assertThat(actual.getType()).isEqualTo(type.toLowerCase());
        assertThat(actual.getDuration()).isEqualTo(duration);
    }

    @Test
    public void returnsAllTypesForWorkouts() throws Exception
    {
        template.postForLocation("/api/entry", manualEntry);

        String[] types = template.getForObject("/api/workouts/types", String[].class);
        assertThat(types).containsExactly(type.toLowerCase());
    }

    @Test
    public void deletesWorkout() throws Exception {
        template.postForLocation("/api/entry", manualEntry);

        Workout[] workouts = template.getForObject("/api/workouts/dates/" + date, Workout[].class);
        assertThat(workouts).isNotNull();
        assertThat(workouts).hasSize(1);

        template.delete("/api/workouts/" + workouts[0].getId());

        workouts = template.getForObject("/api/workouts/dates/" + date, Workout[].class);
        assertThat(workouts).isEmpty();
    }
}
