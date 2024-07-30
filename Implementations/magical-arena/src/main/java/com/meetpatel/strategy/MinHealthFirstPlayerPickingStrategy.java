package com.meetpatel.strategy;

import com.meetpatel.model.Player;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * A player picking strategy that selects the player with the minimum health first.
 * This strategy is used to prioritize players with the lowest health, making them the
 * first player in the game. Subsequent players are selected in a round-robin manner.
 */
public class MinHealthFirstPlayerPickingStrategy implements IPlayerPickingStrategy {
    private final List<Player> players;
    private int currentPlayerIndex;

    public MinHealthFirstPlayerPickingStrategy(final List<Player> players) {
        Objects.requireNonNull(players, "Players list cannot be null");
        this.players = players.stream().sorted(Comparator.comparing(Player::getHealth)).toList();
        this.currentPlayerIndex = 0;
    }

    @Override
    public Player getFirstPlayer() {
        return players.get(0);
    }

    @Override
    public Player getNextPlayer() {
        currentPlayerIndex++;
        currentPlayerIndex %= players.size();
        return players.get(currentPlayerIndex);
    }
}
