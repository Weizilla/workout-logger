package com.weizilla.workout.logger.store.mongo;

import com.weizilla.workout.logger.store.mongo.InstantConverters.InstantReadConverter;
import com.weizilla.workout.logger.store.mongo.InstantConverters.InstantWriteConverter;
import com.weizilla.workout.logger.test.TestUtils;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(actual).isEqualTo(instantlong);
    }

    @Test
    public void convertsLongToInstant() throws Exception
    {
        InstantReadConverter converter = new InstantReadConverter();
        Instant actual = converter.convert(instantlong);
        assertThat(actual).isEqualTo(instant);
    }

    @Test
    public void privateConstructor() throws Exception
    {
        TestUtils.assertPrivateConstructor(InstantConverters.class);
    }
}