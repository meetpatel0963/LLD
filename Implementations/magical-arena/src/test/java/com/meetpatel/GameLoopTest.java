package com.meetpatel;

import com.meetpatel.io.output.IOutputPrinter;
import com.meetpatel.io.output.SysOutOutputPrinter;
import com.meetpatel.model.Dice;
import com.meetpatel.model.Player;
import com.meetpatel.strategy.DefaultWinnerStrategy;
import com.meetpatel.strategy.IPlayerPickingStrategy;
import com.meetpatel.strategy.IWinnerStrategy;
import com.meetpatel.strategy.MinHealthFirstPlayerPickingStrategy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameLoopTest {
    private static List<Player> players;
    private static IPlayerPickingStrategy playerPickingStrategy;
    private static IWinnerStrategy winningStrategy;
    private static IOutputPrinter printer;
    private static Dice attackingDice;
    private static Dice defendingDice;

    @BeforeAll
    public static void setup() {
        players = List.of(new Player("1", "TestA", 50, 10, 5),
                          new Player("2", "TestB", 100, 5, 10));
        playerPickingStrategy = new MinHealthFirstPlayerPickingStrategy(players);
        winningStrategy = new DefaultWinnerStrategy();
        printer = new SysOutOutputPrinter();
        attackingDice = new Dice(6, new Random());
        defendingDice = new Dice(6, new Random());
    }

    @Test
    public void testInvalidPlayersCountThrowsException() {
        Player playerA = new Player("1", "TestA", 1, 1, 1);
        List<Player> players = List.of(playerA);
        IPlayerPickingStrategy playerPickingStrategy = new MinHealthFirstPlayerPickingStrategy(players);
        assertThrows(IllegalArgumentException.class,
                        () -> new GameLoop(players, playerPickingStrategy, winningStrategy,
                                printer, attackingDice, defendingDice));
    }

    @ParameterizedTest
    @CsvSource({
            "100, 10, 10, 100, 10, 10",  // Equal inputs
            "50, 5, 5, 100, 10, 10",     // Different inputs
            "1, 1, 1, 1, 1, 1",          // Minimum edge case
            "2147483647, 2147483647, 2147483647, 2147483647, 2147483647, 2147483647", // Maximum edge case
            "50, 100, 5, 100, 10, 1",    // One step game -> game ends with the first attack
    })
    public void testGame(int healthA, int attackA, int strengthA, int healthB, int attackB, int strengthB) {
        Player playerA = new Player("1", "TestA", healthA, attackA, strengthA);
        Player playerB = new Player("2", "TestB", healthB, attackB, strengthB);
        List<Player> players = List.of(playerA, playerB);
        IPlayerPickingStrategy playerPickingStrategy = new MinHealthFirstPlayerPickingStrategy(players);

        GameLoop gameLoop = new GameLoop(players, playerPickingStrategy, winningStrategy, printer, attackingDice, defendingDice);
        assertDoesNotThrow(gameLoop::play);
        assertTrue(playerA.getHealth() == 0 || playerB.getHealth() == 0, "One player should be dead at the end.");
    }

    private static Object[][] provideInvalidArguments() {
        return new Object[][] {
                {null, playerPickingStrategy, winningStrategy, printer, attackingDice, defendingDice},
                {players, null, winningStrategy, printer, attackingDice, defendingDice},
                {players, playerPickingStrategy, null, printer, attackingDice, defendingDice},
                {players, playerPickingStrategy, winningStrategy, null, attackingDice, defendingDice},
                {players, playerPickingStrategy, winningStrategy, printer, null, defendingDice},
                {players, playerPickingStrategy, winningStrategy, printer, attackingDice, null},
        };
    }

    @ParameterizedTest
    @MethodSource("provideInvalidArguments")
    public void testConstructorThrowsExceptionWhenParameterIsNull(List<Player> players,
                                                                  IPlayerPickingStrategy playerPickingStrategy,
                                                                  IWinnerStrategy winningStrategy,
                                                                  IOutputPrinter printer,
                                                                  Dice attackingDice,
                                                                  Dice defendingDice) {
        assertThrows(NullPointerException.class, () -> new GameLoop(players, playerPickingStrategy, winningStrategy, printer, attackingDice, defendingDice));
    }

}
