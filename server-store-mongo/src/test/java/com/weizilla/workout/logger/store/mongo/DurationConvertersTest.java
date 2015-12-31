package com.weizilla.workout.logger.store.mongo;

import com.weizilla.workout.logger.store.mongo.DurationConverters.DurationReadConverter;
import com.weizilla.workout.logger.store.mongo.DurationConverters.DurationWriteConverter;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

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
        assertThat(actual, is(durationString));
    }

    @Test
    public void convertsStringToDuration() throws Exception
    {
        DurationReadConverter converter = new DurationReadConverter();
        Duration actual = converter.convert(durationString);
        assertThat(actual, is(duration));
    }
}