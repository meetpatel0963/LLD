import lombok.AllArgsConstructor;
import model.Dice;
import model.Player;
import strategy.IPlayerPickingStrategy;

import java.util.List;

@AllArgsConstructor
public class GameLoop {
    private final List<Player> players;
    private final IPlayerPickingStrategy playerPickingStrategy;
    private final Dice dice;

    public void start() {
        int totalPlayers = players.size();
        int currentPlayerIndex = playerPickingStrategy.getFirstPlayer();
        System.out.println("Starting Game!");
        while (true) {
            final Player currentPlayer = players.get(currentPlayerIndex);
            System.out.println("\nPlayer: " + currentPlayer.getId() + " chance:");

            int steps = dice.roll();
            System.out.println("\nPlayer: " + currentPlayer.getId() + " rolled " + steps);

            currentPlayer.move(steps);
            if (currentPlayer.isWinner()) {
                System.out.println("\nPlayer " + currentPlayer.getId() + " won!");
                break;
            }

            currentPlayerIndex = playerPickingStrategy.getNextPlayer(currentPlayerIndex, totalPlayers);
        }
    }
}
