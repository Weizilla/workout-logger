package com.weizilla.workout.logger.store.mongo;

import com.weizilla.workout.logger.entity.GarminEntry;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

@Profile("mongo")
public interface MongoGarminEntryRepository extends MongoRepository<GarminEntry, Long>
{
}