package com.meetpatel.strategy;

import com.meetpatel.model.Player;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MinHealthFirstPlayerPickingStrategyTest {

    @Test
    public void testForTwoPlayers() {
        Player playerA = new Player(UUID.randomUUID().toString(), "TestA", 10, 5, 10);
        Player playerB = new Player(UUID.randomUUID().toString(), "TestB", 5, 5, 10);
        List<Player> players = List.of(playerA, playerB);
        IPlayerPickingStrategy playerPickingStrategy = new MinHealthFirstPlayerPickingStrategy(players);
        assertEquals(playerB, playerPickingStrategy.getFirstPlayer());
        assertEquals(playerA, playerPickingStrategy.getNextPlayer());
        assertEquals(playerB, playerPickingStrategy.getNextPlayer());
    }

    @Test
    public void testConstructorThrowsExceptionWhenParameterIsNull() {
        assertThrows(NullPointerException.class, () -> new MinHealthFirstPlayerPickingStrategy(null));
    }
}
