package com.meetpatel.multi_level_cache.storage;

import com.meetpatel.multi_level_cache.exceptions.NotFoundException;
import com.meetpatel.multi_level_cache.exceptions.StorageFullException;

public interface Storage<Key, Value> {
    void set(Key key, Value value) throws StorageFullException;
    Value get(Key key) throws NotFoundException;
    void remove(Key key) throws NotFoundException;
    Double getCurrentUsage();
}
