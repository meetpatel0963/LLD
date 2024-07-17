package model;

import exception.InvalidMoveException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import strategy.MoveStrategy;

@AllArgsConstructor
@Data
@ToString(exclude = "position")
@EqualsAndHashCode(exclude = "position")
public class Piece {
    private final String id;
    private final PieceColor color;
    private final PieceType type;
    private Cell position;
    private boolean isKilled;
    private MoveStrategy moveStrategy;

    public MoveResponse move(Cell toCell, Board board) {
        Cell fromCell = this.position;
        MoveResponse moveResponse = moveStrategy.tryMove(fromCell, toCell, board);
        if (!moveResponse.isCanMove()) {
            throw new InvalidMoveException("Invalid move!");
        }

        if (toCell.hasPiece()) {
            Piece toPiece = toCell.getCurrentPiece();
            toPiece.setKilled(true);
            toPiece.getPosition().setCurrentPiece(null);
            toPiece.setPosition(null);
        }

        this.position.setCurrentPiece(null);
        this.position = toCell;
        toCell.setCurrentPiece(this);
        return moveResponse;
    }
}
