package com.weizilla.workout.logger.store.mongo;

import com.weizilla.workout.logger.store.mongo.DurationConverters.DurationReadConverter;
import com.weizilla.workout.logger.store.mongo.DurationConverters.DurationWriteConverter;
import com.weizilla.workout.logger.test.TestUtils;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class DurationConvertersTest
{
    private Duration duration;
    private String durationString;

    @Before
    public void setUp() throws Exception
    {
        duration = Duration.ofHours(1).plusMinutes(40);
        durationString = "PT1H40M";
    }

    @Test
    public void convertsDurationToString() throws Exception
    {
        DurationWriteConverter converter = new DurationWriteConverter();
        String actual = converter.convert(duration);
        assertThat(actual).isEqualTo(durationString);
    }

    @Test
    public void convertsStringToDuration() throws Exception
    {
        DurationReadConverter converter = new DurationReadConverter();
        Duration actual = converter.convert(durationString);
        assertThat(actual).isEqualTo(duration);
    }

    @Test
    public void privateConstructor() throws Exception
    {
        TestUtils.assertPrivateConstructor(DurationConverters.class);
    }
}