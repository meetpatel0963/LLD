package model;

import lombok.AllArgsConstructor;

import java.util.Random;

@AllArgsConstructor
public class Dice {
    private final int max;
    private final Random randomGenerator;

    public Dice(int max) {
        this.max = max;
        this.randomGenerator = new Random();
    }

    public int roll() {
        return randomGenerator.nextInt(max) + 1;
    }
}
