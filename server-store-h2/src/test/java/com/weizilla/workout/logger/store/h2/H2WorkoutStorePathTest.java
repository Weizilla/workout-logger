package com.weizilla.workout.logger.store.h2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("h2")
@ContextConfiguration(classes = H2WorkoutStorePathTest.TestConfig.class)
public class H2WorkoutStorePathTest
{
    @Autowired
    private H2WorkoutStore store;

    @Test
    public void workoutStoreIsAutoWired() throws Exception
    {
        assertThat(store).isNotNull();
    }

    @Test
    public void defaultPathUsed() throws Exception
    {
        assertThat(store.getPath()).isEqualTo("/var/weizilla/workout-logger/h2-store");
    }

    @Configuration
    @ComponentScan(basePackageClasses = H2WorkoutStore.class)
    protected static class TestConfig
    {
        @Bean
        public static PropertySourcesPlaceholderConfigurer propertiesResolver()
        {
            return new PropertySourcesPlaceholderConfigurer();
        }
    }
}