package com.meetpatel.multi_level_cache.policies;

public class LRUEvictionPolicy<Key> implements EvictionPolicy<Key> {
    @Override
    public void keyAccessed(Key key) {
//        TODO
    }

    @Override
    public Key evictKey() {
//        TODO
        return null;
    }
}
