package com.meetpatel;

import com.meetpatel.io.output.IOutputPrinter;
import com.meetpatel.model.Dice;
import com.meetpatel.model.Player;
import com.meetpatel.strategy.IPlayerPickingStrategy;
import com.meetpatel.strategy.IWinnerStrategy;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Main game loop that runs the game.
 * This method handles the game’s update and render cycles.
 */
public class GameLoop {
    private final int PLAYERS_REQUIRED = 2;
    private final List<Player> players;
    private final IPlayerPickingStrategy playerPickingStrategy;
    private final IWinnerStrategy winningStrategy;
    private final IOutputPrinter printer;
    private final Dice attackingDice;
    private final Dice defendingDice;

    public GameLoop(final List<Player> players,
                    final IPlayerPickingStrategy playerPickingStrategy,
                    final IWinnerStrategy winningStrategy,
                    final IOutputPrinter printer,
                    final Dice attackingDice, final Dice defendingDice) {
        // Check if the number of players are valid as per the PLAYERS_REQUIRED value
        if (players.size() != PLAYERS_REQUIRED) {
            throw new IllegalArgumentException(String.format("Number of players must be %d", PLAYERS_REQUIRED));
        }

        this.players = Objects.requireNonNull(players, "Players list cannot be null");
        this.playerPickingStrategy = Objects.requireNonNull(playerPickingStrategy, "Player picking strategy cannot be null");
        this.winningStrategy = Objects.requireNonNull(winningStrategy, "Winning strategy cannot be null");
        this.printer = Objects.requireNonNull(printer, "Output printer cannot be null");
        this.attackingDice = Objects.requireNonNull(attackingDice, "Attacking dice cannot be null");
        this.defendingDice = Objects.requireNonNull(defendingDice, "Defending dice cannot be null");
    }

    public void play() {
        printer.printMessage("\n> Starting Magical Arena!");

        // pick first player to be an attacker
        Player attacker = playerPickingStrategy.getFirstPlayer();

        while (true) {
            printer.printMessage(String.format("%nPlayer %s chance: ", attacker.getId()));

            // roll the attacking dice
            int attackerRolledValue = attackingDice.roll();
            printer.printMessage(String.format("Attacker -> Player %s rolled: %d", attacker.getId(), attackerRolledValue));
            // get the damage value for the attacker based on the rolled value
            long attackDamage = attacker.getAttackDamage(attackerRolledValue);

            // get the defenders in the current cycle
            final Player currentAttacker = attacker;
            List<Player> defenders = players.stream()
                    .filter(player -> !player.equals(currentAttacker)).toList();

            defenders.forEach(defender -> {
                // roll the defending dice
                int defenderRolledValue = defendingDice.roll();
                printer.printMessage(String.format("Defender -> Player %s rolled: %d", defender.getId(), defenderRolledValue));
                // get the defense strength for the defender based on the rolled value
                long defenceStrength = defender.getDefenseStrength(defenderRolledValue);

                // calculate net damage
                int damage = (int) Math.max(0L, attackDamage - defenceStrength);
                printer.printMessage(String.format("Player %s attacks Player %s for %d damage.",
                        currentAttacker.getId(), defender.getId(), damage));

                // apply net damage to the defender
                defender.takeDamage(damage);
                printer.printMessage(String.format("Player %s has %d health remaining.",
                        defender.getId(), defender.getHealth()));
            });

            // check if the winner exist
            final Optional<Player> winner = winningStrategy.getWinner(players);
            if (winner.isPresent()) {
                printer.printMessage(String.format("%n%nPlayer %s won!", winner.get().getId()));
                break;
            }

            // update the attacker for the next cycle
            attacker = playerPickingStrategy.getNextPlayer();
        }
    }
}
