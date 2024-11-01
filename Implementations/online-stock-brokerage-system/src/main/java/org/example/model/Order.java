package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Order {
    private final String id;
    private final User user;
    private final Stock stock;
    private final long quantity;
    private final double price;
    private final OrderType type;
}
