package com.weizilla.workout.logger.store;

import com.weizilla.workout.logger.entity.Entry;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public interface Store<T extends Entry<ID>, ID extends Serializable>
{
    void put(T value);
    void delete(ID id);
    void deleteAll();
    Collection<T> getAll();

    default void putAll(Collection<T> values)
    {
        values.forEach(this::put);
    }

    default Set<LocalDate> getAllDates()
    {
        return getAll().stream()
            .map(T::getDate)
            .collect(Collectors.toSet());
    }

    default Collection<T> getForDate(LocalDate date)
    {
        return getAll().stream()
            .filter(v -> v.getDate().isEqual(date))
            .collect(Collectors.toList());
    }

    default Set<ID> getIds()
    {
        return getAll().stream()
            .map(T::getId)
            .collect(Collectors.toSet());
    }
}
