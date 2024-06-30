package com.meetpatel.multi_level_cache.cache;

import com.meetpatel.multi_level_cache.exceptions.NotFoundException;
import com.meetpatel.multi_level_cache.exceptions.StorageFullException;
import com.meetpatel.multi_level_cache.policies.EvictionPolicy;
import com.meetpatel.multi_level_cache.storage.Storage;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CacheProvider<Key, Value> {
    private final EvictionPolicy<Key> evictionPolicy;
    private final Storage<Key, Value> storage;

    public void set(Key key, Value value) {
        try {
            storage.set(key, value);
            evictionPolicy.keyAccessed(key);
        } catch (StorageFullException exception) {
            final Key keyToRemove = evictionPolicy.evictKey();
            if (keyToRemove == null) {
                throw new RuntimeException("Unexpected state. Storage Full and no key to evict.");
            }
            storage.remove(key);
            set(key, value);
        }
    }

    public Value get(Key key) {
        try {
            final Value value = storage.get(key);
            evictionPolicy.keyAccessed(key);
            return value;
        } catch (NotFoundException exception) {
            return null;
        }
    }

    public Double getCurrentUsage() {
        return storage.getCurrentUsage();
    }
}
