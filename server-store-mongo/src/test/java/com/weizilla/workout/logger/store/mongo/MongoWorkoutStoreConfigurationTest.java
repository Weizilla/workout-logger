package com.weizilla.workout.logger.store.mongo;

import com.mongodb.Mongo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.mock.env.MockEnvironment;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

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
        assertThat(actual, is(DB_NAME));
    }

    @Test
    public void returnsMongoClient() throws Exception
    {
        Mongo mongo = configuration.mongo();
        assertThat(mongo, is(notNullValue()));
    }

    @Test
    public void returnsCustomConverters() throws Exception
    {
        CustomConversions conversions = configuration.customConversions();
        assertThat(conversions, is(notNullValue()));
    }
}