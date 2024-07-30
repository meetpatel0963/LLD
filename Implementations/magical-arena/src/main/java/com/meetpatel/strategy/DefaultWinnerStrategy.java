package com.meetpatel.strategy;

import com.meetpatel.model.Player;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implements the IWinnerStrategy strategy.
 * This class processes checks if there's only one player alive among the given players to determine the winner.
 */
public class DefaultWinnerStrategy implements IWinnerStrategy {

    /**
     * @param players: list of players
     * @return the winner or an empty optional based on the winner-check outcome.
     */
    @Override
    public Optional<Player> getWinner(final List<Player> players) {
        Objects.requireNonNull(players, "Players list cannot be null");
        final List<Player> alivePlayers = players.stream().filter(Player::isAlive).toList();
        if (alivePlayers.size() == 1) {
            return Optional.ofNullable(alivePlayers.get(0));
        }
        return Optional.empty();
    }

}
