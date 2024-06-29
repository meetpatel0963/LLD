package com.meetpatel.lld_cache.storage;

import com.meetpatel.lld_cache.exceptions.NotFoundException;
import com.meetpatel.lld_cache.exceptions.StorageFullException;

public interface Storage<Key, Value> {

    void add(Key key, Value value) throws StorageFullException;
    void remove(Key key) throws NotFoundException;
    Value get(Key key) throws NotFoundException;

}
