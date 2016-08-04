package com.weizilla.workout.logger.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ObjectMappers
{
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static
    {
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LocalDate.class, new LocalDateSerializer());
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        module.addSerializer(Duration.class, new DurationSerializer());
        module.addSerializer(Instant.class, new InstantSerializer());
        OBJECT_MAPPER.registerModule(module);
        OBJECT_MAPPER.registerModule(new Jdk8Module());
    }

    private ObjectMappers()
    {
        // private util class
    }

    private static class LocalDateSerializer extends JsonSerializer<LocalDate>
    {
        @Override
        public void serialize(LocalDate localDate, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException, JsonProcessingException
        {
            jsonGenerator.writeString(localDate.toString());
        }
    }

    private static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime>
    {
        @Override
        public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException, JsonProcessingException
        {
            jsonGenerator.writeString(localDateTime.toString());
        }
    }

    private static class DurationSerializer extends JsonSerializer<Duration>
    {
        @Override
        public void serialize(Duration duration, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException, JsonProcessingException
        {
            jsonGenerator.writeString(duration.toString());
        }
    }

    private static class InstantSerializer extends JsonSerializer<Instant>
    {
        @Override
        public void serialize(Instant instant, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException, JsonProcessingException
        {
            jsonGenerator.writeNumber(instant.getEpochSecond());
        }
    }
}
