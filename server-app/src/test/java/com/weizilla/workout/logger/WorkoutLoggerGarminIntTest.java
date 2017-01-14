package com.weizilla.workout.logger;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.mongodb.MongoClient;
import com.weizilla.garmin.entity.Activity;
import com.weizilla.workout.logger.entity.GarminEntry;
import org.junit.Before;
import org.junit.Rule;
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
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.weizilla.workout.logger.test.TestUtils.readResource;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = {"dev", "memory", "server-app-test"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class WorkoutLoggerGarminIntTest
{
    private static final int PORT = 8081;

    @MockBean
    private MongoClient mongoClient;

    @Autowired
    private TestRestTemplate template;

    @Rule
    public WireMockRule wireMock = new WireMockRule(options().port(PORT));

    private long id;
    private String type;
    private Duration duration;
    private LocalDateTime start;
    private double distance;
    private Activity activity;

    @Before
    public void setUp() throws Exception
    {
        id = 10;
        duration = Duration.ofHours(10);
        start = LocalDateTime.now();
        type = "TYPE";
        distance = 20.0;

        activity = new Activity(id, type, duration, start, distance);

        stubFor(get(urlMatching("/sso/login.*"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "text/html; charset=utf-8")
                .withBody(readResource("garmin-int-test/lt-lookup-response.html"))));
        stubFor(post(urlMatching("/sso/login.*"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "text/html; charset=utf-8")
                .withBody(readResource("garmin-int-test/login-response.html"))));
        stubFor(get(urlMatching("/post-auth/login.*"))
            .willReturn(aResponse()
                .withHeader("location", "http://localhost:" + PORT + "/redirect")));
        stubFor(get(urlMatching("/proxy/activity-search-service-1.2.*"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(readResource("garmin-int-test/get-activities-response.json"))));

    }

    @Test
    public void templateIsWiredBySpring() throws Exception
    {
        assertThat(template).isNotNull();
    }

    @Test
    public void addsAndGetsGarminActivity() throws Exception
    {
        List<Activity> activities = Collections.singletonList(activity);
        template.postForLocation("/api/garmin/activity", activities);

        GarminEntry[] garminEntries = template.getForObject("/api/garmin/entry", GarminEntry[].class);
        assertThat(garminEntries).isNotNull();
        assertThat(garminEntries).hasSize(1);

        GarminEntry garminEntry = garminEntries[0];
        assertThat(garminEntry.getId()).isEqualTo(id);
        assertThat(garminEntry.getDate()).isEqualTo(start.toLocalDate());
        assertThat(garminEntry.getActivity()).isEqualTo(activity);
    }

    @Test
    public void returnsDownloadedGarminActivities() throws Exception
    {
        template.getForObject("/api/garmin/refresh", Map.class);
        GarminEntry[] garminEntries = template.getForObject("/api/garmin/entry", GarminEntry[].class);
        assertThat(garminEntries).isNotNull();
        assertThat(garminEntries).hasSize(4);
    }
}
