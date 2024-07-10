package org.example.service;

import lombok.Getter;
import org.example.model.Account;
import org.example.model.Transaction;
import org.example.model.TransactionType;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BankService {
    private final List<Account> accounts;
    private final List<Transaction> transactions;

    public BankService() {
        this.accounts = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public boolean validateUser(String accountNumber, String pin) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber) && account.getPin().equals(pin)) {
                return true;
            }
        }
        return false;
    }

    public int checkBalance(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account.getBalance();
            }
        }
        return 0;
    }

    public void withdraw(String accountNumber, int amount) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                if (account.getBalance() >= amount) {
                    account.setBalance(account.getBalance() - amount);
                    Transaction transaction = new Transaction(account, TransactionType.WITHDRAW, amount);
                    transactions.add(transaction);
                    return;
                }
            }
        }
    }

    public void deposit(String accountNumber, int amount) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                account.setBalance(account.getBalance() + amount);
                Transaction transaction = new Transaction(account, TransactionType.DEPOSIT, amount);
                transactions.add(transaction);
            }
        }
    }

    public Account getAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
}
