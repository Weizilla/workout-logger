package com.weizilla.workout.logger.store.mongo;

import com.weizilla.workout.logger.store.mongo.LocalDateConverters.LocalDateReadConverter;
import com.weizilla.workout.logger.store.mongo.LocalDateConverters.LocalDateWriteConverter;
import com.weizilla.workout.logger.test.TestUtils;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(actual).isEqualTo(dateString);
    }

    @Test
    public void convertsStringToLocalDate() throws Exception
    {
        LocalDateReadConverter converter = new LocalDateReadConverter();
        LocalDate actual = converter.convert(dateString);
        assertThat(actual).isEqualTo(date);
    }

    @Test
    public void privateConstructor() throws Exception
    {
        TestUtils.assertPrivateConstructor(LocalDateConverters.class);
    }
}