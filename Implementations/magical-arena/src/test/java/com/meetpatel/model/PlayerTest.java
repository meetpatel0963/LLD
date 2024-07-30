package com.meetpatel.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class PlayerTest {

    @Test
    public void testPlayerCreation() {
        String id = UUID.randomUUID().toString();
        Player playerA = new Player(id, "TestA", 20, 5, 10);
        assertEquals(id, playerA.getId());
        assertEquals("TestA", playerA.getName());
        assertEquals(20, playerA.getHealth());
        assertEquals(5, playerA.getAttack());
        assertEquals(10, playerA.getStrength());
    }

    @ParameterizedTest
    @CsvSource({
            "-10,  5,  5,   'All negative health'",
            "0,    5,  5,   'Zero health'",
            "10,  -5,  5,   'Negative attack'",
            "10,   0,  5,   'Zero attack'",
            "10,   5, -5,   'Negative strength'",
            "10,   5,  0,   'Zero strength'",
            "-10, -5,  5,   'Negative health and attack'",
            "0,    0,  5,   'Zero health and attack'",
            "10,  -5, -5,   'Negative attack and strength'",
            "10,   0,  0,   'Zero attack and strength'",
            "-10, -5, -5,   'All negative attributes'",
            "0,    0,  0,   'All zero attributes'"
    })
    public void testPlayerCreationWithInvalidAttributes(int health, int attack, int strength, String scenario) {
        assertThrows(IllegalArgumentException.class, () -> new Player("1", "PlayerA", health, attack, strength), scenario);
    }

    @ParameterizedTest
    @CsvSource({
            "10, 5, 5",
            "20, 15, 10",
            "30, 25, 20"
    })
    public void testPlayerCreationWithValidAttributes(int health, int attack, int strength) {
        assertDoesNotThrow(() -> new Player("1", "PlayerA", health, attack, strength));
    }

}
