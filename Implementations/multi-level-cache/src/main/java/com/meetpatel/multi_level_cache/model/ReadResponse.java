package com.meetpatel.multi_level_cache.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public class ReadResponse<Value> {
    private Value value;
    private Double timeTaken;
}
