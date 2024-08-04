package org.example.chain;

public class Dispenser10 extends DispenseHandler {
    @Override
    public void dispenseCash(int amount) {
        if (amount >= 10) {
            int num = amount / 10;
            int remainder = amount % 10;
            System.out.println("Dispensing " + num + " $10 note");
            if (remainder != 0) {
                getNextDispenseHandler().dispenseCash(remainder);
            }
        } else {
            getNextDispenseHandler().dispenseCash(amount);
        }
    }
}