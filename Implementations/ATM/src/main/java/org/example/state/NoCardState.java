package org.example.state;

import org.example.service.ATM;

public class NoCardState implements ATMState {
    private final ATM atm;

    public NoCardState(ATM atm) {
        this.atm = atm;
    }

    @Override
    public void insertCard() {
        System.out.println("Card inserted");
        atm.changeState(new CardInsertedState(atm));
    }

    @Override
    public void ejectCard() {
        System.out.println("No card to eject");
    }

    @Override
    public void insertPin(String pin) {
        System.out.println("Please insert card first");
    }

    @Override
    public void withdrawAmount(int amount) {
        System.out.println("Please insert card first");
    }

    @Override
    public void depositAmount(int amount) {
        System.out.println("Please insert card first");
    }

    @Override
    public void checkBalance() {
        System.out.println("Please insert card first");
    }
}
