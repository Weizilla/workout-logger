package com.weizilla.workout.logger.store.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.CustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoStoreConfiguration
{
    private final List<Converter<?, ?>> converters = new ArrayList<>();

    @Autowired
    public MongoStoreConfiguration(List<Converter<?, ?>> converters)
    {
        this.converters.addAll(converters);
    }

    @Bean
    public CustomConversions customConversions()
    {
        return new CustomConversions(converters);
    }
}
