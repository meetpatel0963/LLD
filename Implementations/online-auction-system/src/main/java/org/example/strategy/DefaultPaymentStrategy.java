package org.example.strategy;

import org.example.model.User;

public class DefaultPaymentStrategy implements PaymentStrategy {
    @Override
    public void pay(double amount, User user) {
        System.out.println("Default payment of $" + amount + " made by " + user.getUsername());
    }
}
