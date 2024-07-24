package org.example.strategy;

import org.example.model.User;

public interface PaymentStrategy {
    void pay(double amount, User user);
}
