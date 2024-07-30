package com.meetpatel.strategy;

import com.meetpatel.model.Player;

import java.util.List;
import java.util.Optional;

/**
 * Defines a strategy for determining the winner from a list of players.
 * Implementations of this interface should provide specific logic for selecting
 * the winner based on the provided list of players.
 */
public interface IWinnerStrategy {
    /**
     * Determines the winner from the given list of players.
     * This method should apply the strategy's logic to choose the winning player
     * or return an empty Optional if no winner can be determined.
     *
     * @param players the list of players to consider for winning
     * @return an Optional containing the winning player, or an empty Optional
     *         if no winner can be determined
     */
    Optional<Player> getWinner(List<Player> players);
}
