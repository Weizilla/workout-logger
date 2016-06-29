package com.weizilla.workout.logger.store.mongo;

import com.weizilla.workout.logger.entity.ManualEntry;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

@Profile("mongo")
public interface MongoManualEntryRepository extends MongoRepository<ManualEntry, UUID>
{
}
