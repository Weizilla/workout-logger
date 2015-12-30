package com.weizilla.workout.logger.store.mongo;

import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.store.WorkoutStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Profile("mongo")
@Component
public class MongoStore implements WorkoutStore
{
    private static final Logger logger = LoggerFactory.getLogger(MongoStore.class);

    @PostConstruct
    public void init()
    {
        logger.info("Init mongo store");
    }

    @Override
    public void put(Workout workout)
    {
        logger.info("Put into mongo");
    }

    @Override
    public void delete(UUID uuid)
    {
        logger.info("delete from mongo");
    }

    @Override
    public List<Workout> getAll()
    {
        logger.info("get from mongo");
        return Collections.emptyList();
    }
}
