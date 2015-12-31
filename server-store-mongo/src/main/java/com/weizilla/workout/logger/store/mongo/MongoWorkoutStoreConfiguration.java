package com.weizilla.workout.logger.store.mongo;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;

@Profile("mongo")
@Configuration
public class MongoWorkoutStoreConfiguration extends AbstractMongoConfiguration
{
    protected static final String DB_NAME = "store.mongo.dbname";
    protected static final String HOST = "store.mongo.host";

    @Autowired
    private Environment environment;

    @Override
    public CustomConversions customConversions()
    {
        return new DurationConverters();
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
        return new MongoClient(host);
    }

    protected void setEnvironment(Environment environment)
    {
        this.environment = environment;
    }
}
