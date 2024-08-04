package org.example.state;

import org.example.service.ATM;

class PINEnteredState implements ATMState {
    private final ATM atm;

    public PINEnteredState(ATM atm) {
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
        System.out.println("PIN already entered");
    }

    @Override
    public void withdrawAmount(int amount) {
        if (atm.getAmount() >= amount) {
            if(atm.getBankService().checkBalance(atm.getCurrentAccount().getAccountNumber()) >= amount) {
                if (!atm.getDispenseHandler().canDispense(amount)) {
                    System.out.println("Cannot dispense the given amount.");
                } else {
                    atm.getBankService().withdraw(atm.getCurrentAccount().getAccountNumber(), amount);
                    atm.getDispenseHandler().dispenseCash(amount);
                    atm.changeState(new CashDispensedState(atm));
                }
            } else {
                System.out.println("Insufficient balance");
            }
        } else {
            System.out.println("Not enough cash available in the ATM.");
        }
    }

    @Override
    public void depositAmount(int amount) {
        atm.getBankService().deposit(atm.getCurrentAccount().getAccountNumber(), amount);
        System.out.println("Deposited " + amount);
        atm.changeState(new CashDepositedState(atm));
    }

    @Override
    public void checkBalance() {
        int balance = atm.getBankService().checkBalance(atm.getCurrentAccount().getAccountNumber());
        System.out.println("Current balance: " + balance);
    }
}