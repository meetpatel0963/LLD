package com.meetpatel.lld_cache;

import com.meetpatel.lld_cache.cache.Cache;
import com.meetpatel.lld_cache.factories.CacheFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CacheTest {

    private Cache<Integer, Integer> cache;

    @BeforeEach
    public void setup() {
        CacheFactory<Integer,Integer> cacheFactory = CacheFactory.getInstance();
        cache = cacheFactory.getDefaultCache(3);
    }

    @Test
    public void itShouldBeAbleToGetAndAddItemsToCache() {
        cache.put(1, 1);
        cache.put(2, 2);

        assertEquals(1, cache.get(1));
        cache.put(3, 3);
        assertEquals(3, cache.get(3));

        cache.put(4, 4);
        assertNull(cache.get(2));
    }
}
