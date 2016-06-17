package com.weizilla.workout.logger.store.mongo;

import com.weizilla.workout.logger.entity.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class MongoStoreSpike implements CommandLineRunner
{
    @Autowired
    private MongoWorkoutRepository mongoRepo;

    public static void main(String[] args)
    {
        SpringApplication app = new SpringApplication(MongoStoreSpike.class);
        app.setAdditionalProfiles("mongo");
        app.run(args);
    }

    @Override
    public void run(String... strings) throws Exception
    {
        Workout workout = new Workout("ABC", Duration.ofMinutes(45));
        mongoRepo.save(workout);
        System.out.println("count: " + mongoRepo.count());
        mongoRepo.findAll().forEach(System.out::println);

        LocalDate date = LocalDate.of(2015, 12, 11);
        Workout pastWorkout = new Workout(null, "ABC", Duration.ofHours(1), date, Instant.now(), "COMMENT");
        mongoRepo.save(pastWorkout);

        List<Workout> byDate = mongoRepo.findByDate(date);
        System.out.println("found by date: " + byDate);
    }

    @Configuration
    @ActiveProfiles("mongo")
    public static class MongoStoreSpikeConfig
    {
        // nothing special
    }
}