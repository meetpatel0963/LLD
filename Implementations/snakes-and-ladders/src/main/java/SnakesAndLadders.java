import model.Board;
import model.Dice;
import model.Player;
import strategy.RoundRobinPlayerPickingStrategy;

import java.util.ArrayList;
import java.util.List;

public class SnakesAndLadders {
    public static void main(String[] args) {
        final int ROWS = 10;
        final int COLS = 10;
        final int DICE_MAX = 6;

        Board board = new Board(ROWS, COLS);
        board.addSnakeOrLadder(4, 20);
        board.addSnakeOrLadder(16, 45);
        board.addSnakeOrLadder(27, 80);
        board.addSnakeOrLadder(45, 94);
        board.addSnakeOrLadder(30, 2);
        board.addSnakeOrLadder(62, 37);
        board.addSnakeOrLadder(92, 18);

        List<Player> players = new ArrayList<>();
        players.add(new Player("1", "Alice", 1, board));
        players.add(new Player("2", "Bob", 1, board));

        GameLoop gameLoop = new GameLoop(players, new RoundRobinPlayerPickingStrategy(), new Dice(DICE_MAX));
        gameLoop.start();
    }
}
