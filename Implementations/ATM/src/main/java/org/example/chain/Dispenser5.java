package org.example.chain;

public class Dispenser5 extends DispenseHandler {
    @Override
    public void dispenseCash(int amount) {
        if (amount >= 5) {
            int num = amount / 5;
            int remainder = amount % 5;
            System.out.println("Dispensing " + num + " $5 note");
            if (remainder != 0) {
                getNextDispenseHandler().dispenseCash(remainder);
            }
        } else {
            getNextDispenseHandler().dispenseCash(amount);
        }
    }
}
