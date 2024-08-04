package org.example.chain;

public class NullEffectDispenser extends DispenseHandler {

    @Override
    public void dispenseCash(int amount) {
        throw new IllegalStateException("Invalid State!");
    }
}
