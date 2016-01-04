package com.weizilla.workout.logger.store.mongo;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;

import java.util.ArrayList;
import java.util.List;

@Profile("mongo")
@Configuration
public class MongoWorkoutStoreConfiguration extends AbstractMongoConfiguration
{
    private static final Logger logger = LoggerFactory.getLogger(MongoWorkoutStoreConfiguration.class);
    protected static final String DB_NAME = "store.mongo.dbname";
    protected static final String HOST = "store.mongo.host";
    private static final String DEFAULT_HOST = "localhost";

    @Autowired
    private Environment environment;

    @Autowired
    private List<Converter<?, ?>> converters = new ArrayList<>();

    @Override
    public CustomConversions customConversions()
    {
        return new CustomConversions(converters);
    }

    @Override
    protected String getDatabaseName()
    {
        return environment.getProperty(DB_NAME);
    }

    @Override
    public Mongo mongo() throws Exception
    {
        String host = environment.getProperty(HOST);
        host = host != null ? host : DEFAULT_HOST;
        logger.info("Connecting to mongo store at {}", host);
        return new MongoClient(host);
    }

    protected void setEnvironment(Environment environment)
    {
        this.environment = environment;
    }
}
