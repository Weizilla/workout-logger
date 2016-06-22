package com.weizilla.workout.logger.store.h2;

import com.weizilla.workout.logger.store.BaseWorkoutStoreTest;
import org.junit.Before;

public class H2WorkoutStoreTest extends BaseWorkoutStoreTest
{
    @Before
    public void setUp() throws Exception
    {
        store = new H2WorkoutStore(null);
    }
}