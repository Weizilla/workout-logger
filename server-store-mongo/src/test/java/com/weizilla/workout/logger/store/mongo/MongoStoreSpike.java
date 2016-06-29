package com.weizilla.workout.logger.store.mongo;

import com.weizilla.garmin.entity.Activity;
import com.weizilla.workout.logger.entity.ManualEntry;
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
import java.util.UUID;

@SpringBootApplication
public class MongoStoreSpike implements CommandLineRunner
{
    @Autowired
    private MongoWorkoutRepository workoutRepo;

    @Autowired
    private MongoManualEntryRepository manualEntryRepository;

    @Autowired
    private MongoGarminEntryRepository activityRepo;

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
        LocalDate date = LocalDate.of(2015, 12, 11);
        Workout workout = new Workout(null, "ABC", Duration.ofHours(1), date, Instant.now(), "COMMENT", 1L,
            UUID.randomUUID());
        workoutRepo.save(workout);

        Activity activity = new Activity(1234, "RUNNING", Duration.ofDays(1), Instant.now(), 22.3);
        activityRepo.save(activity);

        ManualEntry entry = new ManualEntry("DEF", Duration.ofDays(1));
        manualEntryRepository.save(entry);

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