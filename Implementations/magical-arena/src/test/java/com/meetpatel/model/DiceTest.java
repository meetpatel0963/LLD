package com.meetpatel.model;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DiceTest {

    @Test
    public void testDiceCreation() {
        Dice dice = new Dice(6, new Random());
        assertEquals(6, dice.getSides());
    }

    @Test
    public void testDiceRoll() {
        Dice dice = new Dice(6, new Random());
        for (int i = 1 ; i <= 10 ; i++) {
            int rolledValue = dice.roll();
            assertTrue(rolledValue >= 1 && rolledValue <= 6);
        }
    }

}
