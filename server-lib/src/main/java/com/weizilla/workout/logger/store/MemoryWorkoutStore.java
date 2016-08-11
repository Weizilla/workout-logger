package com.weizilla.workout.logger.store;

import com.weizilla.workout.logger.entity.Workout;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Profile("memory")
@Component
public class MemoryWorkoutStore extends MemoryStore<Workout, UUID> implements WorkoutStore
{
}
