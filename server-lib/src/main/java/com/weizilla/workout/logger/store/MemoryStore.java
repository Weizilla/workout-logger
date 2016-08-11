package com.weizilla.workout.logger.store;

import com.weizilla.workout.logger.entity.Entry;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryStore<T extends Entry<ID>, ID extends Serializable> implements Store<T, ID>
{
    private final Map<ID, T> store = new ConcurrentHashMap<>();

    @Override
    public void put(T value)
    {
        store.put(value.getId(), value);
    }

    @Override
    public void delete(ID id)
    {
        store.remove(id);
    }

    @Override
    public void deleteAll()
    {
        store.clear();
    }

    @Override
    public Collection<T> getAll()
    {
        return store.values();
    }
}
