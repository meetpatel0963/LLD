package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@AllArgsConstructor
@Data
public class Cell {
    private final int x;
    private final int y;
    private Piece currentPiece;

    public boolean hasPiece() {
        return this.getCurrentPiece() != null;
    }
}
