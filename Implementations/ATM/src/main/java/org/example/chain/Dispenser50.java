package org.example.chain;

public class Dispenser50 extends DispenseHandler {
    @Override
    public void dispenseCash(int amount) {
        if (amount >= 50) {
            int num = amount / 50;
            int remainder = amount % 50;
            System.out.println("Dispensing " + num + " $50 note");
            if (remainder != 0) {
                getNextDispenseHandler().dispenseCash(remainder);
            }
        } else {
            getNextDispenseHandler().dispenseCash(amount);
        }
    }
}
