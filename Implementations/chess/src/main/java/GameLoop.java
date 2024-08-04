import lombok.AllArgsConstructor;
import model.Board;
import model.Cell;
import model.MoveResponse;
import model.Player;
import strategy.IPlayerPickingStrategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@AllArgsConstructor
public class GameLoop {
    private final Board board;
    private final List<Player> players;
    private final IPlayerPickingStrategy playerPickingStrategy;

    public void start() throws IOException {
        int totalPlayers = players.size();
        boolean canOnlyMoveKing = false;
        int curPlayerIndex = playerPickingStrategy.getFirstPlayer();
        Player curPlayer = players.get(curPlayerIndex);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("Player " + curPlayer.getName() + "'s turn: ");
            String[] tokens = br.readLine().split(" " );
            int fromX = Integer.parseInt(tokens[0]);
            int fromY = Integer.parseInt(tokens[1]);
            int toX = Integer.parseInt(tokens[2]);
            int toY = Integer.parseInt(tokens[3]);

            Cell fromCell = getCell(fromX, fromY);
            Cell toCell = getCell(toX, toY);

            System.out.println("Move " + fromCell.getCurrentPiece().getType() + " from " + board.getPosition(fromX, fromY) + " to " + board.getPosition(toX, toY));
            MoveResponse moveResponse = curPlayer.makeMove(fromCell, toCell, canOnlyMoveKing);
            canOnlyMoveKing = moveResponse.isCreatesCheck();

            int nextPlayerIndex = playerPickingStrategy.getNextPlayer(curPlayerIndex, totalPlayers);
            Player nextPlayer = players.get(nextPlayerIndex);
            if (nextPlayer.hasLost()) {
                System.out.println("Player " + curPlayer.getName() + " won!");
                return;
            }

            curPlayer = nextPlayer;
        }
    }

    public void runSimulation(List<int[]> predefinedMoves) throws IOException {
        int totalPlayers = players.size();
        int curPlayerIndex = playerPickingStrategy.getFirstPlayer();
        Player curPlayer = players.get(curPlayerIndex);

        for (int[] move : predefinedMoves) {
            System.out.println("Player " + curPlayer.getName() + "'s turn: ");
            int fromX = move[0];
            int fromY = move[1];
            int toX = move[2];
            int toY = move[3];

            Cell fromCell = getCell(fromX, fromY);
            Cell toCell = getCell(toX, toY);

            System.out.println("Move " + fromCell.getCurrentPiece().getType() + " from " + board.getPosition(fromX, fromY) + " to " + board.getPosition(toX, toY));
            fromCell.getCurrentPiece().move(toCell, board);

            int nextPlayerIndex = playerPickingStrategy.getNextPlayer(curPlayerIndex, totalPlayers);
            Player nextPlayer = players.get(nextPlayerIndex);
            if (nextPlayer.hasLost()) {
                System.out.println("Player " + curPlayer.getName() + " won!");
                return;
            }

            curPlayerIndex = nextPlayerIndex;
            curPlayer = nextPlayer;
        }
    }

    private Cell getCell(int x, int y) {
        return board.getCells().get(x).get(y);
    }
}
