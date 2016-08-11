package com.weizilla.workout.logger.entity;

import java.time.LocalDate;

public interface Entry<ID>
{
    ID getId();
    LocalDate getDate();
}
