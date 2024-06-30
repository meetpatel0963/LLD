package com.meetpatel.multi_level_cache.cache;

import com.meetpatel.multi_level_cache.model.ReadResponse;
import com.meetpatel.multi_level_cache.model.WriteResponse;
import lombok.NonNull;

import java.util.Collections;
import java.util.List;

public class NullEffectLevelCache<Key, Value> implements ILevelCache<Key, Value> {
    @Override
    public @NonNull ReadResponse<Value> get(Key key) {
        return new ReadResponse<>(null, 0.0);
    }

    @Override
    public @NonNull WriteResponse set(Key key, Value value) {
        return new WriteResponse(0.0);
    }

    @Override
    public @NonNull List<Double> getUsages() {
        return Collections.emptyList();
    }
}
