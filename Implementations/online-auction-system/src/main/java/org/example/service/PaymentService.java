package org.example.service;

import org.example.model.User;
import org.example.strategy.PaymentStrategy;

public class PaymentService {
    private static volatile PaymentService paymentService;
    private final PaymentStrategy paymentStrategy;

    private PaymentService(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public static PaymentService getInstance(PaymentStrategy paymentStrategy) {
        if (paymentService == null) {
            synchronized (PaymentService.class) {
                if (paymentService == null) {
                    paymentService = new PaymentService(paymentStrategy);
                }
            }
        }
        return paymentService;
    }

    public void handlePayment(double amount, User user) {
        paymentStrategy.pay(amount, user);
    }
}
