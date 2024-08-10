package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class PricePoint {
    private final String symbolId;
    private final LocalDateTime timestamp;
    private final double price;
}
