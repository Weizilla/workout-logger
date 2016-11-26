package com.weizilla.workout.logger.store.mongo;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

public class MongoStoreConfigurationTest
{
    private static final String HOST = "HOST";
    private static final String DB_NAME = "DB NAME";
    private MongoStoreConfiguration configuration;

    @Before
    public void setUp() throws Exception
    {
        configuration = new MongoStoreConfiguration();
        MockEnvironment environment = new MockEnvironment();
        configuration.setEnvironment(environment);
        environment.setProperty(MongoStoreConfiguration.DB_NAME, DB_NAME);
        environment.setProperty(MongoStoreConfiguration.HOST, HOST);
    }

    @Test
    public void returnsDbName() throws Exception
    {
        String actual = configuration.getDatabaseName();
        assertThat(actual).isEqualTo(DB_NAME);
    }

    @Test
    public void returnsCustomConverters() throws Exception
    {
        CustomConversions conversions = configuration.customConversions();
        assertThat(conversions).isNotNull();
    }
}