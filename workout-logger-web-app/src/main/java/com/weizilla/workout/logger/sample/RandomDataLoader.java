package com.weizilla.workout.logger.sample;

import com.weizilla.workout.logger.entity.Workout;
import com.weizilla.workout.logger.store.WorkoutStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Random;

@Component
public class RandomDataLoader
{
    private final WorkoutStore workoutStore;

    @Autowired
    public RandomDataLoader(WorkoutStore workoutStore)
    {
        this.workoutStore = workoutStore;
    }

    @PostConstruct
    public void init()
    {
        addWorkout();
    }

    public void addWorkout()
    {
        Random random = new Random();
        String[] types = {"SWIM", "BIKE", "RUN"};
        String type = types[random.nextInt(types.length)];
        Duration duration = Duration.ofMinutes(random.nextInt(120));
        LocalDate date = LocalDate.now().minusDays(random.nextInt(100));
        Workout workout = new Workout(type, duration, date);
        workoutStore.store(workout);
    }
}
