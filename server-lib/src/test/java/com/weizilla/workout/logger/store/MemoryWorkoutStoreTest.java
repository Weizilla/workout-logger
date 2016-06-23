package com.weizilla.workout.logger.store;

import org.junit.Before;

public class MemoryWorkoutStoreTest extends BaseWorkoutStoreTest
{
    @Before
    public void setUp() throws Exception
    {
        super.setUp();
        store = new MemoryWorkoutStore();
    }
}