package com.meetpatel.multi_level_cache.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LevelCacheData {
    private final int readTime;
    private final int writeTime;
}
