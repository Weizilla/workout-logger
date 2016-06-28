package com.weizilla.workout.logger.store.mongo;

import com.weizilla.garmin.entity.Activity;
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
    private MongoWorkoutRepository workoutRepo;

    @Autowired
    private MongoActivityRepository activityRepo;

    public static void main(String[] args)
    {
        SpringApplication app = new SpringApplication(MongoStoreSpike.class);
        app.setWebEnvironment(false);
        app.setAdditionalProfiles("mongo");
        app.run(args);
    }

    @Override
    public void run(String... strings) throws Exception
    {
        Workout workout = new Workout("ABC", Duration.ofMinutes(45));
        workoutRepo.save(workout);
        System.out.println("count: " + workoutRepo.count());
        workoutRepo.findAll().forEach(System.out::println);

        LocalDate date = LocalDate.of(2015, 12, 11);
        Workout pastWorkout = new Workout(null, "ABC", Duration.ofHours(1), date, Instant.now(), "COMMENT", 1L);
        workoutRepo.save(pastWorkout);

        Activity activity = new Activity(1234, "RUNNING", Duration.ofDays(1), Instant.now(), 22.3);
        activityRepo.save(activity);

        List<Workout> byDate = workoutRepo.findByDate(date);
        System.out.println("found by date: " + byDate);
    }

    @Configuration
    @ActiveProfiles("mongo")
    public static class MongoStoreSpikeConfig
    {
        // nothing special
    }
}