package com.weizilla.workout.logger.web;

import com.weizilla.workout.logger.WorkoutLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = WorkoutLogger.class)
public class WebApplication
{
    public static void main(String[] args) throws Exception
    {
        SpringApplication.run(WebApplication.class, args);
    }
}
