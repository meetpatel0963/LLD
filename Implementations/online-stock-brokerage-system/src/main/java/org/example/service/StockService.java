package org.example.service;

import lombok.NonNull;
import org.example.model.PricePoint;
import org.example.model.Stock;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class StockService {
    private final Map<String, Stock> stocks;

    public StockService() {
        stocks = new ConcurrentHashMap<>();
    }

    public Stock addStock(@NonNull final String name, final double initialPrice) {
        Stock stock = new Stock(generateSymbolId(), name, initialPrice);
        stocks.put(stock.getId(), stock);
        return stock;
    }

    public void addPricePoint(@NonNull final String symbolId,
                              final double price) {
        stocks.get(symbolId).addPricePoint(new PricePoint(symbolId, LocalDateTime.now(), price));
    }

    public double getPrice(@NonNull final String symbolId) {
        Stock stock = stocks.get(symbolId);
        return stock.getHistory().get(stock.getHistory().size() - 1).getPrice();
    }

    private String generateSymbolId() {
        return UUID.randomUUID().toString();
    }

    public Stock getStock(final String symbolId) {
        return stocks.get(symbolId);
    }
}
