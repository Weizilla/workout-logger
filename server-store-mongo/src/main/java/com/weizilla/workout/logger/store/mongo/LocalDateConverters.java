package com.weizilla.workout.logger.store.mongo;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

public class LocalDateConverters
{
    @Component
    @ReadingConverter
    public static class LocalDateReadConverter implements Converter<String, LocalDate>
    {
        @Override
        public LocalDate convert(String s)
        {
            return LocalDate.parse(s);
        }
    }

    @Component
    @WritingConverter
    public static class LocalDateWriteConverter implements Converter<LocalDate, String>
    {
        @Override
        public String convert(LocalDate localDate)
        {
            return localDate.toString();
        }
    }
}
