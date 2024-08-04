import model.*;
import strategy.DefaultPlayerPickingStrategy;
import strategy.MoveStrategy;
import strategy.MoveStrategyFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChessDemo {
    public static void main(String[] args) {
        // Initialize the board with 8x8 cells
        int rows = 8;
        int cols = 8;
        List<List<Cell>> cells = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            List<Cell> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                row.add(new Cell(i, j, null));
            }
            cells.add(row);
        }

        // Initialize pieces for both players and place them on the board
        List<Piece> whitePieces = new ArrayList<>();
        List<Piece> blackPieces = new ArrayList<>();

        initializePieces(whitePieces, blackPieces, cells);

        Board board = new Board(rows, cols, cells);

        // Create players
        Player player1 = new Player("1", "Player 1", PieceColor.BLACK, board, whitePieces);
        Player player2 = new Player("2", "Player 2", PieceColor.WHITE, board, blackPieces);

        List<Player> players = Arrays.asList(player1, player2);

        // Initialize the game loop with default player picking strategy
        GameLoop gameLoop = new GameLoop(board, players, new DefaultPlayerPickingStrategy());

        // Define the sequence of moves for quick checkmate
        List<int[]> predefinedMoves = Arrays.asList(
                new int[]{6, 4, 5, 4},   // e2 to e3
                new int[]{1, 5, 2, 5},   // f7 to f6
                new int[]{7, 3, 3, 7},   // d1 to h5
                new int[]{0, 4, 1, 5},   // e8 to f7
                new int[]{3, 7, 1, 5}    // h5 to f7
        );

        try {
            gameLoop.runSimulation(predefinedMoves);
//            gameLoop.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initializePieces(List<Piece> whitePieces, List<Piece> blackPieces, List<List<Cell>> cells) {
        // Add pawns
        for (int i = 0; i < 8; i++) {
            whitePieces.add(createPiece("P" + i, PieceColor.WHITE, PieceType.PAWN, cells.get(6).get(i)));
            blackPieces.add(createPiece("p" + i, PieceColor.BLACK, PieceType.PAWN, cells.get(1).get(i)));
        }

        // Add rooks
        whitePieces.add(createPiece("R1", PieceColor.WHITE, PieceType.ROOK, cells.get(7).get(0)));
        whitePieces.add(createPiece("R2", PieceColor.WHITE, PieceType.ROOK, cells.get(7).get(7)));
        blackPieces.add(createPiece("r1", PieceColor.BLACK, PieceType.ROOK, cells.get(0).get(0)));
        blackPieces.add(createPiece("r2", PieceColor.BLACK, PieceType.ROOK, cells.get(0).get(7)));

        // Add knights
        whitePieces.add(createPiece("N1", PieceColor.WHITE, PieceType.KNIGHT, cells.get(7).get(1)));
        whitePieces.add(createPiece("N2", PieceColor.WHITE, PieceType.KNIGHT, cells.get(7).get(6)));
        blackPieces.add(createPiece("n1", PieceColor.BLACK, PieceType.KNIGHT, cells.get(0).get(1)));
        blackPieces.add(createPiece("n2", PieceColor.BLACK, PieceType.KNIGHT, cells.get(0).get(6)));

        // Add bishops
        whitePieces.add(createPiece("B1", PieceColor.WHITE, PieceType.BISHOP, cells.get(7).get(2)));
        whitePieces.add(createPiece("B2", PieceColor.WHITE, PieceType.BISHOP, cells.get(7).get(5)));
        blackPieces.add(createPiece("b1", PieceColor.BLACK, PieceType.BISHOP, cells.get(0).get(2)));
        blackPieces.add(createPiece("b2", PieceColor.BLACK, PieceType.BISHOP, cells.get(0).get(5)));

        // Add queens
        whitePieces.add(createPiece("Q", PieceColor.WHITE, PieceType.QUEEN, cells.get(7).get(3)));
        blackPieces.add(createPiece("q", PieceColor.BLACK, PieceType.QUEEN, cells.get(0).get(3)));

        // Add kings
        whitePieces.add(createPiece("K", PieceColor.WHITE, PieceType.KING, cells.get(7).get(4)));
        blackPieces.add(createPiece("k", PieceColor.BLACK, PieceType.KING, cells.get(0).get(4)));
    }

    private static Piece createPiece(String id, PieceColor color, PieceType type, Cell position) {
        MoveStrategy moveStrategy = MoveStrategyFactory.getMoveStrategy(type);
        Piece piece = new Piece(id, color, type, position, false, moveStrategy);
        position.setCurrentPiece(piece);
        return piece;
    }
}
