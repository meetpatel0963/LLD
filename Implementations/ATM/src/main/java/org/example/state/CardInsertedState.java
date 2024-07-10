package org.example.state;

import org.example.service.ATM;

class CardInsertedState implements ATMState {
    private final ATM atm;

    public CardInsertedState(ATM atm) {
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
        if (atm.getBankService().validateUser(atm.getCurrentAccount().getAccountNumber(), pin)) {
            System.out.println("PIN validated");
            atm.changeState(new PINEnteredState(atm));
        } else {
            System.out.println("Invalid PIN");
        }
    }

    @Override
    public void withdrawAmount(int amount) {
        System.out.println("Please enter PIN first");
    }

    @Override
    public void depositAmount(int amount) {
        System.out.println("Please enter PIN first");
    }

    @Override
    public void checkBalance() {
        System.out.println("Please enter PIN first");
    }
}