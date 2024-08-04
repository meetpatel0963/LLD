package org.example;

import org.example.chain.*;
import org.example.model.Account;
import org.example.model.Card;
import org.example.service.ATM;
import org.example.service.BankService;

public class ATMDemo {
    public static void main(String[] args) {
        DispenseHandler dispenser50 = new Dispenser50();
        DispenseHandler dispenser20 = new Dispenser20();
        DispenseHandler dispenser10 = new Dispenser10();
        DispenseHandler dispenser5 = new Dispenser5();
        DispenseHandler nullDispenser = new NullEffectDispenser();

        // Set the chain of responsibility
        dispenser50.setNextDispenseHandler(dispenser20);
        dispenser20.setNextDispenseHandler(dispenser10);
        dispenser10.setNextDispenseHandler(dispenser5);
        dispenser5.setNextDispenseHandler(nullDispenser);

        Account account1 = new Account("12345", "0000", 1000);
        Account account2 = new Account("67890", "1111", 500);
        Card card1 = new Card("12345");
        Card card2 = new Card("67890");

        BankService bankService = new BankService();
        bankService.addAccount(account1);
        bankService.addAccount(account2);

        ATM atm = new ATM(bankService, dispenser50, 5000);

        try {
            // Test Card 1 (account1)
            atm.insertCard(card1);
            atm.insertPin("0000");
            atm.checkBalance();
            atm.withdrawAmount(275);
            atm.checkBalance();
            atm.ejectCard();

            atm.insertCard(card1);
            atm.insertPin("0000");
            atm.checkBalance();
            atm.depositAmount(100); // Deposit $100
            atm.checkBalance();
            atm.ejectCard();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            // Test Card 2 (account2)
            atm.insertCard(card2);
            atm.insertPin("1111");
            atm.withdrawAmount(268);
            atm.checkBalance();
            atm.ejectCard();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
