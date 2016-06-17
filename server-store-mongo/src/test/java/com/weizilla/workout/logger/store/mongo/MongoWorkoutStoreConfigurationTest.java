package com.weizilla.workout.logger.store.mongo;

import com.mongodb.Mongo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

public class MongoWorkoutStoreConfigurationTest
{
    private static final String HOST = "HOST";
    private static final String DB_NAME = "DB NAME";
    private MongoWorkoutStoreConfiguration configuration;

    @Before
    public void setUp() throws Exception
    {
        configuration = new MongoWorkoutStoreConfiguration();
        MockEnvironment environment = new MockEnvironment();
        configuration.setEnvironment(environment);
        environment.setProperty(MongoWorkoutStoreConfiguration.DB_NAME, DB_NAME);
        environment.setProperty(MongoWorkoutStoreConfiguration.HOST, HOST);
    }

    @Test
    public void returnsDbName() throws Exception
    {
        String actual = configuration.getDatabaseName();
        assertThat(actual).isEqualTo(DB_NAME);
    }

    @Test
    public void returnsMongoClient() throws Exception
    {
        Mongo mongo = configuration.mongo();
        assertThat(mongo).isNotNull();
    }

    @Test
    public void returnsCustomConverters() throws Exception
    {
        CustomConversions conversions = configuration.customConversions();
        assertThat(conversions).isNotNull();
    }
}