package com.weizilla.workout.logger.store;

import com.weizilla.workout.logger.entity.ManualEntry;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Profile("memory")
@Component
public class MemoryManualEntryStore extends MemoryStore<ManualEntry, UUID> implements ManualEntryStore
{
}