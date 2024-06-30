package com.meetpatel.multi_level_cache.cache;

import com.meetpatel.multi_level_cache.model.ReadResponse;
import com.meetpatel.multi_level_cache.model.WriteResponse;
import lombok.NonNull;

import java.util.List;

public interface ILevelCache<Key, Value> {
    @NonNull
    WriteResponse set(Key key, Value value);

    @NonNull
    ReadResponse<Value> get(Key key);

    @NonNull
    List<Double> getUsages();
}
