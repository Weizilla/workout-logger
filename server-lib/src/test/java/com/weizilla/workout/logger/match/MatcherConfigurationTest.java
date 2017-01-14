package com.weizilla.workout.logger.match;

import com.weizilla.workout.logger.WorkoutLogger;
import com.weizilla.workout.logger.match.MatcherConfigurationTest.TestConfiguration;
import com.weizilla.workout.logger.store.GarminEntryStore;
import com.weizilla.workout.logger.store.ManualEntryStore;
import com.weizilla.workout.logger.store.WorkoutStore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = TestConfiguration.class)
@ActiveProfiles("matcher-test")
public class MatcherConfigurationTest {

    @Autowired
    private MatcherConfiguration configuration;

    @Test
    public void configurationIsCreated() throws Exception {
        assertThat(configuration).isNotNull();
    }

    @Test
    public void readsMappingFromConfiguration() throws Exception {
        assertThat(configuration.getTypeMapping())
            .containsOnly(entry("a", "b"), entry("c", "d"), entry("hello_type", "abc-def"));

    }

    @SpringBootApplication(scanBasePackageClasses = MatcherConfiguration.class)
    @Profile("matcher-test")
    public static class TestConfiguration {
        @Bean
        public WorkoutLogger workoutLogger() {
            return mock(WorkoutLogger.class);
        }

        @Bean
        public GarminEntryStore garminEntryStore() {
            return mock(GarminEntryStore.class);
        }

        @Bean
        public WorkoutStore workoutStore() {
            return mock(WorkoutStore.class);
        }

        @Bean
        public ManualEntryStore manualEntryStore() {
            return mock(ManualEntryStore.class);
        }
    }
}