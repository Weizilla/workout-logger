package com.weizilla.workout.logger.store;

import com.weizilla.workout.logger.entity.GarminEntry;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("memory")
@Component
public class MemoryGarminEntryStore extends MemoryStore<GarminEntry, Long> implements GarminEntryStore
{
}