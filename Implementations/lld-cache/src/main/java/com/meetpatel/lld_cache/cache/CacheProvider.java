package com.meetpatel.lld_cache.cache;

import com.meetpatel.lld_cache.exceptions.NotFoundException;
import com.meetpatel.lld_cache.exceptions.StorageFullException;
import com.meetpatel.lld_cache.policies.EvictionPolicy;
import com.meetpatel.lld_cache.storage.Storage;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CacheProvider<Key, Value> implements Cache<Key, Value> {
    private final EvictionPolicy<Key> evictionPolicy;
    private final Storage<Key, Value> storage;

    @Override
    public void put(Key key, Value value) {
        try {
            storage.add(key, value);
            evictionPolicy.keyAccessed(key);
        } catch (StorageFullException exception) {
            System.out.println("Got storage full. Will try to evict.");
            Key keyToRemove = evictionPolicy.evictKey();
            if (keyToRemove == null) {
                throw new RuntimeException("Unexpected state. Storage full and no key to evict.");
            }
            storage.remove(keyToRemove);
            System.out.println("Creating space by evicting key: " + keyToRemove);
            put(key, value);
        }
    }

    @Override
    public Value get(Key key) {
        try {
            Value value = storage.get(key);
            evictionPolicy.keyAccessed(key);
            return value;
        } catch (NotFoundException exception) {
            System.out.println("Key: " + key + " doesn't exist.");
            return null;
        }
    }
}
