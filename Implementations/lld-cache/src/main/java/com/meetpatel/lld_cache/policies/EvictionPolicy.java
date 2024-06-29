package com.meetpatel.lld_cache.policies;

public interface EvictionPolicy<Key> {

    void keyAccessed(Key key);
    Key evictKey();

}
