package com.weizilla.workout.logger.store.mongo;

import com.weizilla.workout.logger.entity.Workout;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

@Profile("mongo")
public interface WorkoutMongoRepo extends MongoRepository<Workout, UUID>
{
}
