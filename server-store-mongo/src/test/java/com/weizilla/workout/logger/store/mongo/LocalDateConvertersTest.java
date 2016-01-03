package com.weizilla.workout.logger.store.mongo;

import com.weizilla.workout.logger.store.mongo.LocalDateConverters.LocalDateReadConverter;
import com.weizilla.workout.logger.store.mongo.LocalDateConverters.LocalDateWriteConverter;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class LocalDateConvertersTest
{
    private LocalDate date;
    private String dateString;

    @Before
    public void setUp() throws Exception
    {
        date = LocalDate.of(2016, 1, 3);
        dateString = "2016-01-03";
    }

    @Test
    public void convertsLocalDateToString() throws Exception
    {
        LocalDateWriteConverter converter = new LocalDateWriteConverter();
        String actual = converter.convert(date);
        assertThat(actual, is(dateString));
    }

    @Test
    public void convertsStringToLocalDate() throws Exception
    {
        LocalDateReadConverter converter = new LocalDateReadConverter();
        LocalDate actual = converter.convert(dateString);
        assertThat(actual, is(date));
    }
}