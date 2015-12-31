package com.weizilla.workout.logger.store.mongo;

import com.google.common.collect.Lists;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.convert.CustomConversions;

import java.time.Duration;

public class DurationConverters extends CustomConversions
{
    public DurationConverters()
    {
        super(Lists.newArrayList(new DurationReadConverter(), new DurationWriteConverter()));
    }

    @ReadingConverter
    public static class DurationReadConverter implements Converter<String, Duration>
    {
        @Override
        public Duration convert(String duration)
        {
            return Duration.parse(duration);
        }
    }

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
