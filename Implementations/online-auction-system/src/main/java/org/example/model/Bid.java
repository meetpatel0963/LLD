package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Bid {
    private final String id;
    private final User bidder;
    private final double amount;
}
