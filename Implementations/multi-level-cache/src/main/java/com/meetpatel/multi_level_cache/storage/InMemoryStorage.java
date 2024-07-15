package com.meetpatel.multi_level_cache.storage;

import com.meetpatel.multi_level_cache.exceptions.NotFoundException;
import com.meetpatel.multi_level_cache.exceptions.StorageFullException;

import java.util.HashMap;
import java.util.Map;

public class InMemoryStorage<Key, Value> implements Storage<Key, Value> {
    private final Map<Key, Value> storage;
    private final int capacity;

    public InMemoryStorage(Integer capacity) {
        this.capacity = capacity;
        storage = new HashMap<>();
    }

    @Override
    public void set(Key key, Value value) throws StorageFullException {
        if (isStorageFull()) throw new StorageFullException("Storage Full!");
        storage.put(key, value);
    }

    @Override
    public Value get(Key key) throws NotFoundException {
        if (!storage.containsKey(key)) throw new NotFoundException("Key: " + key + " doesn't exist.");
        return storage.get(key);
    }

    @Override
    public void remove(Key key) throws NotFoundException {
        if (!storage.containsKey(key)) throw new NotFoundException("Key: " + key + " doesn't exist.");
        storage.remove(key);
    }

    private boolean isStorageFull() {
        return storage.size() == capacity;
    }

    @Override
    public Double getCurrentUsage() {
        return (double) storage.size() / (double) capacity;
    }
}
