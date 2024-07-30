package com.meetpatel;

import com.meetpatel.io.output.IOutputPrinter;
import com.meetpatel.io.output.SysOutOutputPrinter;
import com.meetpatel.model.Dice;
import com.meetpatel.model.Player;
import com.meetpatel.strategy.DefaultWinnerStrategy;
import com.meetpatel.strategy.IPlayerPickingStrategy;
import com.meetpatel.strategy.IWinnerStrategy;
import com.meetpatel.strategy.MinHealthFirstPlayerPickingStrategy;

import java.util.List;
import java.util.Random;

public class MagicalArena {
    public static void main(String[] args) {
        // Running Game Loop for the sample test case given in the assignment

        Player alice = new Player("1", "Alice", 50, 10, 5);
        Player bob = new Player("2", "Bob", 100, 5, 10);

        List<Player> players = List.of(alice, bob);
        IPlayerPickingStrategy playerPickingStrategy = new MinHealthFirstPlayerPickingStrategy(players);
        IWinnerStrategy winningStrategy = new DefaultWinnerStrategy();
        IOutputPrinter printer = new SysOutOutputPrinter();

        Dice attackingDice = new Dice(6, new Random());
        Dice defendingDice = new Dice(6, new Random());

        GameLoop gameLoop = new GameLoop(players, playerPickingStrategy, winningStrategy, printer, attackingDice, defendingDice);
        gameLoop.play();
    }
}
