package org.example.service;

import lombok.Getter;
import lombok.Setter;
import org.example.chain.DispenseHandler;
import org.example.model.Account;
import org.example.model.Card;
import org.example.state.ATMState;
import org.example.state.NoCardState;

@Getter
public class ATM {
    private ATMState currentState;
    private final BankService bankService;
    private final DispenseHandler dispenseHandler;
    private Account currentAccount;
    @Setter private int amount;

    public ATM(BankService bankService, DispenseHandler dispenseHandler, int amount) {
        this.bankService = bankService;
        this.dispenseHandler = dispenseHandler;
        this.amount = amount;
        this.currentState = new NoCardState(this);
    }

    public void changeState(ATMState state) {
        this.currentState = state;
    }

    public void insertCard(Card card) {
        this.currentAccount = bankService.getAccount(card.getAccountNumber());
        currentState.insertCard();
    }

    public void ejectCard() {
        currentState.ejectCard();
        this.currentAccount = null;
    }

    public void insertPin(String pin) {
        currentState.insertPin(pin);
    }

    public void withdrawAmount(int amount) {
        currentState.withdrawAmount(amount);
    }

    public void depositAmount(int amount) {
        currentState.depositAmount(amount);
    }

    public void checkBalance() {
        currentState.checkBalance();
    }
}
