package com.weizilla.workout.logger.store.mongo;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import java.time.Duration;

public class DurationConverters
{
    private DurationConverters()
    {
        // static util class
    }

    @Component
    @ReadingConverter
    public static class DurationReadConverter implements Converter<String, Duration>
    {
        @Override
        public Duration convert(String duration)
        {
            return Duration.parse(duration);
        }
    }

    @Component
    @WritingConverter
    public static class DurationWriteConverter implements Converter<Duration, String>
    {
        @Override
        public String convert(Duration duration)
        {
            return duration.toString();
        }
    }
}
