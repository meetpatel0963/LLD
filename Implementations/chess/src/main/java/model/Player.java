package model;

import exception.InvalidMoveException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Data
public class Player {
    private final String id;
    private final String name;
    private final PieceColor color;
    private final Board board;
    private final List<Piece> pieces;

    public boolean hasLost() {
        Optional<Piece> piece = pieces.stream().filter(_piece -> _piece.getType().equals(PieceType.KING)).findFirst();
        if (piece.isEmpty()) {
            throw new RuntimeException("Invalid piece id.");
        }
        return piece.get().isKilled();
    }

    public MoveResponse makeMove(Cell fromCell, Cell toCell, boolean canOnlyMoveKing) {
        if (!fromCell.hasPiece()
            || !fromCell.getCurrentPiece().getColor().equals(this.color)
            || (canOnlyMoveKing && !fromCell.getCurrentPiece().getType().equals(PieceType.KING))
            || (toCell.hasPiece() && toCell.getCurrentPiece().getColor().equals(this.color))) {
            throw new InvalidMoveException("Invalid move!");
        }
        return fromCell.getCurrentPiece().move(toCell, board);
    }
}
