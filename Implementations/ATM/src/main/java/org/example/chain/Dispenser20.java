package org.example.chain;

public class Dispenser20 extends DispenseHandler {
    @Override
    public void dispenseCash(int amount) {
        if (amount >= 20) {
            int num = amount / 20;
            int remainder = amount % 20;
            System.out.println("Dispensing " + num + " $20 note");
            if (remainder != 0) {
                getNextDispenseHandler().dispenseCash(remainder);
            }
        } else {
            getNextDispenseHandler().dispenseCash(amount);
        }
    }
}