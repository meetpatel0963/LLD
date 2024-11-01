package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@AllArgsConstructor
@Getter
public class Portfolio {
    private final String userId;
    private final List<PortfolioItem> portfolioItems;

    public Portfolio(String userId) {
        this.userId = userId;
        portfolioItems = new CopyOnWriteArrayList<>();
    }

    public void addItem(@NonNull final PortfolioItem item) {
        portfolioItems.add(item);
    }
}
