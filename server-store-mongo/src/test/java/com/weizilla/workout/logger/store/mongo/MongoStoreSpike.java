package com.weizilla.workout.logger.store.mongo;

import com.weizilla.workout.logger.entity.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.time.Duration;
import java.time.LocalDate;

@SpringBootApplication
public class MongoStoreSpike implements CommandLineRunner
{
    @Autowired
    private MongoWorkoutRepository mongoRepo;

    public static void main(String[] args)
    {
        SpringApplication.run(MongoStoreSpike.class, args);
    }

    @Override
    public void run(String... strings) throws Exception
    {
        Workout workout = new Workout("ABC", Duration.ofMinutes(45), LocalDate.now());
        mongoRepo.save(workout);
        System.out.println("count: " + mongoRepo.count());
        mongoRepo.findAll().forEach(System.out::println);
    }

    @Configuration
    @PropertySource("test.properties")
    public static class MongoStoreSpikeConfig
    {
        // nothing special
    }
}