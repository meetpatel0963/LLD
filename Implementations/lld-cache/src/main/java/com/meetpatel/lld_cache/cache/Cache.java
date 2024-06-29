package com.meetpatel.lld_cache.cache;

public interface Cache<Key, Value> {
    void put(Key key, Value value);
    Value get(Key key);
}
