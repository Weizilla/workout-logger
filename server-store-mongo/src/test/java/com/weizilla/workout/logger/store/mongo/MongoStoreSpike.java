package com.weizilla.workout.logger.store.mongo;

import com.weizilla.workout.logger.entity.GarminEntry;
import com.weizilla.workout.logger.entity.ManualEntry;
import com.weizilla.workout.logger.entity.ManualEntryStub;
import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.entity.WorkoutBuilder;
import com.weizilla.workout.logger.garmin.GarminEntryStub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class MongoStoreSpike implements CommandLineRunner
{
    @Autowired
    private MongoWorkoutRepository workoutRepo;

    @Autowired
    private MongoManualEntryRepository manualEntryRepo;

    @Autowired
    private MongoGarminEntryRepository garminEntryRepo;

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
        Workout workout = new WorkoutBuilder()
            .setType("ABC")
            .setDuration(Duration.ofHours(1))
            .setDate(date)
            .setComment("COMMENT")
            .setGarminId(1L)
            .setManualId(UUID.randomUUID())
            .build();
        workoutRepo.save(workout);

        List<Workout> byDate = workoutRepo.findByDate(date);
        System.out.println("Found workout by date: " + byDate);
        Workout byId = workoutRepo.findOne(workout.getId());
        System.out.println("Found workout by id: " + byId);

        GarminEntry garminEntry = GarminEntryStub.create();
        garminEntryRepo.save(garminEntry);

        System.out.println("Found garmin entry by id: " + garminEntryRepo.findOne(garminEntry.getId()));

        ManualEntry entry = ManualEntryStub.create();
        manualEntryRepo.save(entry);

        System.out.println("Found manual entry by id: " + manualEntryRepo.findOne(entry.getId()));
    }

    @Configuration
    @ActiveProfiles("mongo")
    public static class MongoStoreSpikeConfig
    {
        // nothing special
    }
}