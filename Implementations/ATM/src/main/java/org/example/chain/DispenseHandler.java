package org.example.chain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class DispenseHandler {
    private DispenseHandler nextDispenseHandler;

    public abstract void dispenseCash(int amount);

    public boolean canDispense(int amount) {
        if (amount % 5 != 0) {
            System.out.println("Amount must be in multiples of $5");
            return false;
        }
        return true;
    }
}
