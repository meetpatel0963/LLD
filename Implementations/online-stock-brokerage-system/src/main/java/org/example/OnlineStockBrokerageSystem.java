package org.example;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.example.exception.StockNotFoundException;
import org.example.exception.UserNotFoundException;
import org.example.model.Order;
import org.example.model.OrderType;
import org.example.model.Stock;
import org.example.model.User;
import org.example.service.OrderService;
import org.example.service.StockService;
import org.example.service.UserService;

@AllArgsConstructor
public class OnlineStockBrokerageSystem {
    private final UserService userService;
    private final StockService stockService;
    private final OrderService orderService;

    public void registerUser(@NonNull final String username,
                             @NonNull final String password,
                             @NonNull final String email,
                             final double balance) {
        userService.registerUser(username, password, email, balance);
    }

    public User loginUser(String username, String password) {
        return userService.loginUser(username, password);
    }

    public Stock addStock(@NonNull final String name, final double initialPrice) {
        return stockService.addStock(name, initialPrice);
    }

    synchronized public void addPricePoint(@NonNull final String symbolId,
                              final double price) {
        stockService.addPricePoint(symbolId, price);
    }

    synchronized public double getPrice(@NonNull final String symbolId) {
        return stockService.getPrice(symbolId);
    }

    synchronized public Order placeOrder(final String userId,
                            final String symbolId,
                            final long quantity,
                            final double price,
                            @NonNull final OrderType type) {
        User user = userService.getUser(userId);
        if (user == null) {
            throw new UserNotFoundException("No user found for given user id.");
        }

        Stock stock = stockService.getStock(symbolId);
        if (stock == null) {
            throw new StockNotFoundException("No stock found for given symbol id.");
        }

        return orderService.placeOrder(user, stock, quantity, price, type);
    }

}
