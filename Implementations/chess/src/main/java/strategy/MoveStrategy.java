package strategy;

import lombok.AllArgsConstructor;
import model.*;

import java.util.*;

@AllArgsConstructor
public class MoveStrategy {
    private final List<Integer> X;
    private final List<Integer> Y;
    private final boolean canMoveOneStepOnly;

    public MoveResponse tryMove(Cell fromCell, Cell toCell, Board board) {
        if (fromCell == toCell) return new MoveResponse(true, false);

        Queue<Cell> queue = new LinkedList<>();
        queue.add(fromCell);
        HashSet<Cell> visited = new HashSet<>();
        visited.add(fromCell);

        final int N = board.getRows();
        final int M = board.getCols();

        while (!queue.isEmpty()) {
            Cell curCell = queue.remove();
            int cx = curCell.getX();
            int cy = curCell.getY();

            for (int i = 0 ; i < X.size() ; i++) {
                int nx = cx + X.get(i);
                int ny = cy + Y.get(i);
                if (!isValid(nx, ny, N, M)) {
                    continue;
                }

                Cell nextCell = board.getCells().get(nx).get(ny);
                if (visited.contains(nextCell)) {
                    continue;
                }
                if (!nextCell.equals(toCell) && nextCell.hasPiece()) {
                    continue;
                }
                visited.add(nextCell);
                queue.add(nextCell);
            }

            if (canMoveOneStepOnly) break;
        }

        PieceColor opponentColor = fromCell.getCurrentPiece().getColor().equals(PieceColor.BLACK) ? PieceColor.WHITE : PieceColor.BLACK;
        Cell opponentKingCell = board.getCells().stream()
                                .flatMap(Collection::stream)
                                .filter(cell -> {
                                    Piece curPiece = cell.getCurrentPiece();
                                    return curPiece != null
                                            && curPiece.getType().equals(PieceType.KING)
                                            && curPiece.getColor().equals(opponentColor);
                                }).findFirst().get();
        return new MoveResponse(visited.contains(toCell), visited.contains(opponentKingCell));
    }

    private boolean isValid(int i, int j, int n, int m) {
        return i >= 0 && i < n && j >= 0 && j < m;
    }
}
