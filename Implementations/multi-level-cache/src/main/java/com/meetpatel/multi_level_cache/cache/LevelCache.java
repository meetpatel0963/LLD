package com.meetpatel.multi_level_cache.cache;

import com.meetpatel.multi_level_cache.model.LevelCacheData;
import com.meetpatel.multi_level_cache.model.ReadResponse;
import com.meetpatel.multi_level_cache.model.WriteResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class LevelCache<Key, Value> implements ILevelCache<Key, Value> {
    private final LevelCacheData levelCacheData;
    private final CacheProvider<Key, Value> cacheProvider;

    @NonNull
    private final ILevelCache<Key, Value> next;

    @Override
    public @NonNull ReadResponse<Value> get(Key key) {
        Double time = 0.0;
        Value value = cacheProvider.get(key);
        time += levelCacheData.getReadTime();

        if (value == null) {
            ReadResponse<Value> readResponse = next.get(key);
            time += readResponse.getTimeTaken();
            value = readResponse.getValue();

            if (value != null) {
                cacheProvider.set(key, value);
                time += levelCacheData.getWriteTime();
            }
        }

        return new ReadResponse<>(value, time);
    }

    @Override
    public @NonNull WriteResponse set(Key key, Value value) {
        Double time = 0.0;
        Value curLevelValue = cacheProvider.get(key);
        time += levelCacheData.getReadTime();

        if (curLevelValue == null || !curLevelValue.equals(value)) {
            cacheProvider.set(key, value);
            time += levelCacheData.getWriteTime();
        }

        time += next.set(key, value).getTimeTaken();
        return new WriteResponse(time);
    }

    @Override
    public @NonNull List<Double> getUsages() {
        final List<Double> usages = new ArrayList<>(Collections.singleton(cacheProvider.getCurrentUsage()));
        usages.addAll(next.getUsages());
        return usages;
    }
}
