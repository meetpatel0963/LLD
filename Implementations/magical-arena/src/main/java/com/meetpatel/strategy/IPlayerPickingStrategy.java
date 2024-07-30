package com.meetpatel.strategy;

import com.meetpatel.model.Player;

/**
 * Defines a strategy for selecting players in a game.
 * Implementations of this interface should provide specific logic for determining
 * the first player and the subsequent players in the game.
 */
public interface IPlayerPickingStrategy {
    /**
     * Retrieves the first player in the game.
     * @return the first player as determined by the strategy
     */
    Player getFirstPlayer();

    /**
     * Retrieves the next player in the sequence.
     * This method should return the player who is next in line after the current one.
     * @return the next player as determined by the strategy
     */
    Player getNextPlayer();
}
