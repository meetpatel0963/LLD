package org.example.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Transaction {
    private final Account account;
    private final TransactionType type;
    private final double amount;
    private final LocalDateTime timestamp;

    public Transaction(Account account, TransactionType type, double amount) {
        this.account = account;
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }
}
