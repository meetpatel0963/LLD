package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class PortfolioItem {
    private final Stock symbol;
    @Setter private double avgPrice;
    @Setter private long quantity;
}
