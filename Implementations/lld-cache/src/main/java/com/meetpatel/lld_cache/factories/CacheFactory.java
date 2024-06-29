package com.meetpatel.lld_cache.factories;

import com.meetpatel.lld_cache.cache.CacheProvider;
import com.meetpatel.lld_cache.policies.LRUEvictionPolicy;
import com.meetpatel.lld_cache.storage.HashMapBasedStorage;

public class CacheFactory<Key, Value> {
    private static volatile CacheFactory cacheFactory;

    private CacheFactory() {}

    public static <Key, Value> CacheFactory<Key, Value> getInstance() {
        if (cacheFactory == null) {
            synchronized (CacheFactory.class) {
                if (cacheFactory == null) {
                    cacheFactory = new CacheFactory<>();
                }
            }
        }
        return cacheFactory;
    }

    public CacheProvider<Key, Value> getDefaultCache(final int capacity) {
        return new CacheProvider<>(new LRUEvictionPolicy<>(),
                new HashMapBasedStorage<>(capacity));
    }
}
