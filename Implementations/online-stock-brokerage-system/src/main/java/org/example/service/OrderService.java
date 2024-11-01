package org.example.service;

import lombok.NonNull;
import org.example.exception.InsufficientBalanceException;
import org.example.exception.InsufficientQuantityException;
import org.example.model.*;

import java.util.Optional;
import java.util.UUID;

public class OrderService {
    public Order placeOrder(@NonNull final User user,
                            @NonNull final Stock stock,
                            final long quantity,
                            final double price,
                            @NonNull final OrderType type) {
        Order order = new Order(generateOrderId(), user, stock, quantity, price, type);

        validateOrder(user, order);
        user.setBalance(user.getBalance() - order.getQuantity() * order.getPrice());
        user.addOrder(order);
        updatePortfolio(user, order);
        return order;
    }

    private void validateOrder(@NonNull final User user, @NonNull final Order order) {
        if (order.getType().equals(OrderType.BUY)
            && user.getBalance() < order.getQuantity() * order.getPrice()) {
            throw new InsufficientBalanceException("Not enough balance to execute the order.");
        }
        if (order.getType().equals(OrderType.SELL)
            && user.getPortfolio().getPortfolioItems().stream()
                .noneMatch(item -> item.getSymbol().getName().equals(order.getStock().getName())
                                    && item.getQuantity() >= order.getQuantity())) {
            throw new InsufficientQuantityException("Not enough quantity to execute the order.");
        }
    }

    public void updatePortfolio(@NonNull final User user, @NonNull final Order order) {
        Portfolio portfolio = user.getPortfolio();
        Optional<PortfolioItem> portfolioItem =
                portfolio.getPortfolioItems().stream()
                        .filter(item -> item.getSymbol().getName().equals(order.getStock().getName()))
                        .findFirst();

        if (order.getType().equals(OrderType.BUY)) {
            if (portfolioItem.isPresent()) {
                PortfolioItem item = portfolioItem.get();
                item.setAvgPrice((item.getAvgPrice() * item.getQuantity() + order.getPrice() * order.getQuantity()) / (item.getQuantity() + order.getQuantity()));
                item.setQuantity(item.getQuantity() + order.getQuantity());
            } else {
                portfolio.addItem(new PortfolioItem(order.getStock(), order.getPrice(), order.getQuantity()));
            }
        } else {
            PortfolioItem item = portfolioItem.get();
            item.setAvgPrice((item.getAvgPrice() * item.getQuantity() - order.getPrice() * order.getQuantity()) / (item.getQuantity() - order.getQuantity()));
            item.setQuantity(item.getQuantity() - order.getQuantity());
        }
    }

    private String generateOrderId() {
        return UUID.randomUUID().toString();
    }
}
