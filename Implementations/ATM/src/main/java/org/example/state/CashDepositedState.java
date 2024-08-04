package org.example.state;

import org.example.service.ATM;

public class CashDepositedState implements ATMState {
    private final ATM atm;

    public CashDepositedState(ATM atm) {
        this.atm = atm;
    }

    @Override
    public void insertCard() {
        System.out.println("Card is already inserted");
    }

    @Override
    public void ejectCard() {
        System.out.println("Card ejected");
        atm.changeState(new NoCardState(atm));
    }

    @Override
    public void insertPin(String pin) {
        System.out.println("Please eject the card and insert again!");
    }

    @Override
    public void withdrawAmount(int amount) {
        System.out.println("Please eject the card and insert again!");
    }

    @Override
    public void depositAmount(int amount) {
        System.out.println("Please eject the card and insert again!");
    }

    @Override
    public void checkBalance() {
        int balance = atm.getBankService().checkBalance(atm.getCurrentAccount().getAccountNumber());
        System.out.println("Current balance: " + balance);
    }
}