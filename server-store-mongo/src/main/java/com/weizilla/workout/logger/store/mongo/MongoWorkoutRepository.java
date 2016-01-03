package com.weizilla.workout.logger.store.mongo;

import com.weizilla.workout.logger.entity.Workout;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Profile("mongo")
public interface MongoWorkoutRepository extends MongoRepository<Workout, UUID>
{
    List<Workout> findByDate(LocalDate localDate);
}
