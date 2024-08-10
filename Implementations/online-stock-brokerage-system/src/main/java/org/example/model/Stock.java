package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@AllArgsConstructor
@Getter
public class Stock {
    private final String id;
    private final String name;
    private final List<PricePoint> history;

    public Stock(String id, String name, double initialPrice) {
        this.id = id;
        this.name = name;
        this.history = new CopyOnWriteArrayList<>();
        history.add(new PricePoint(id, LocalDateTime.now(), initialPrice));
    }

    public void addPricePoint(@NonNull final PricePoint pricePoint) {
        history.add(pricePoint);
    }
}
