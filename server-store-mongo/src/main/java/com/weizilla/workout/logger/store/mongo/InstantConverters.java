package com.weizilla.workout.logger.store.mongo;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import java.time.Instant;

public class InstantConverters
{
    @Component
    @ReadingConverter
    public static class InstantReadConverter implements Converter<Long, Instant>
    {
        @Override
        public Instant convert(Long instant)
        {
            return Instant.ofEpochSecond(instant);
        }
    }

    @Component
    @WritingConverter
    public static class InstantWriteConverter implements Converter<Instant, Long>
    {
        @Override
        public Long convert(Instant instant)
        {
            return instant.getEpochSecond();
        }
    }
}
