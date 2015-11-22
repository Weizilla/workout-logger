package com.weizilla.workout.logger.web.converter;

import com.weizilla.workout.logger.json.ObjectMappers;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

@Component
public class WorkoutJsonConverter extends MappingJackson2HttpMessageConverter
{
    public WorkoutJsonConverter()
    {
        super(ObjectMappers.OBJECT_MAPPER);
    }
}
