package org.example.state;

public interface ATMState {
    void insertCard();
    void insertPin(String pin);
    void withdrawAmount(int amount);
    void depositAmount(int amount);
    void checkBalance();
    void ejectCard();
}
