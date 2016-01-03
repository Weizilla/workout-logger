package com.weizilla.workout.logger.store.mongo;

import com.weizilla.workout.logger.store.mongo.InstantConverters.InstantReadConverter;
import com.weizilla.workout.logger.store.mongo.InstantConverters.InstantWriteConverter;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class InstantConvertersTest
{
    private Instant instant;
    private long instantlong;

    @Before
    public void setUp() throws Exception
    {
        instant = Instant.ofEpochSecond(1451855205);
        instantlong = 1451855205;
    }

    @Test
    public void convertsInstantToLong() throws Exception
    {
        InstantWriteConverter converter = new InstantWriteConverter();
        long actual = converter.convert(instant);
        assertThat(actual, is(instantlong));
    }

    @Test
    public void convertsLongToInstant() throws Exception
    {
        InstantReadConverter converter = new InstantReadConverter();
        Instant actual = converter.convert(instantlong);
        assertThat(actual, is(instant));
    }
}