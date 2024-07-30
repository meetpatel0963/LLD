package com.meetpatel.model;

import java.util.Random;

/**
 * Represents a Dice in the Magical Arena game.
 * This class encapsulates dice details such as number of sides, and random generator.
 */
public class Dice {
    private final int sides;
    private final Random randomGenerator;

    public Dice(int sides, Random randomGenerator) {
        this.sides = sides;
        this.randomGenerator = randomGenerator;
    }

    public int getSides() {
        return sides;
    }

    /**
     * @return a randomly generated value in the range [1, sides]
     */
    public int roll() {
        return randomGenerator.nextInt(this.sides) + 1;
    }
}
