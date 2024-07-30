package com.meetpatel.strategy;

import com.meetpatel.model.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DefaultPlayerWinnerStrategyTest {

    private static IWinnerStrategy winningStrategy;

    @BeforeAll
    public static void setup() {
        winningStrategy = new DefaultWinnerStrategy();
    }

    @Test
    public void testWhenWinnerExist() {
        Player playerA = new Player(UUID.randomUUID().toString(), "TestA", 10, 5, 10);
        Player playerB = new Player(UUID.randomUUID().toString(), "TestB", 5, 5, 10);
        playerB.takeDamage(10);

        List<Player> players = List.of(playerA, playerB);
        Optional<Player> winner = winningStrategy.getWinner(players);
        assertTrue(winner.isPresent());
        assertEquals(playerA, winner.get());
    }

    @Test
    public void testWhenWinnerDoesNotExist() {
        Player playerA = new Player(UUID.randomUUID().toString(), "TestA", 10, 5, 10);
        Player playerB = new Player(UUID.randomUUID().toString(), "TestB", 5, 5, 10);

        List<Player> players = List.of(playerA, playerB);
        Optional<Player> winner = winningStrategy.getWinner(players);
        assertFalse(winner.isPresent());
    }

    @Test
    public void testWhenPlayersListIsNull() {
        assertThrows(NullPointerException.class, () -> winningStrategy.getWinner(null));
    }

}
