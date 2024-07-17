package strategy;

import model.PieceType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static model.PieceType.*;

public class MoveStrategyFactory {

    public static MoveStrategy getMoveStrategy(PieceType type) {
        List<Integer> X, Y;
        boolean canMoveOneStepOnly = false;
        switch (type) {
            case KING:
                X = Arrays.asList(-1, -1, -1, 0, 0, 1, 1, 1);
                Y = Arrays.asList(-1, 0, 1, -1, 1, -1, 0, 1);
                canMoveOneStepOnly = true;
                break;
            case PAWN:
                X = Arrays.asList(-1, 0, 1, 0);
                Y = Arrays.asList(0, -1, 0, 1);
                canMoveOneStepOnly = true;
                break;
            case KNIGHT:
                X = Arrays.asList(-2, -1, 1, 2, 2, 1, -1, -2);
                Y = Arrays.asList(1, 2, 2, 1, -1, -2, -2, -1);
                canMoveOneStepOnly = true;
                break;
            case QUEEN:
                X = Arrays.asList(-1, -1, -1, 0, 0, 1, 1, 1);
                Y = Arrays.asList(-1, 0, 1, -1, 1, -1, 0, 1);
                break;
            case ROOK:
                X = Arrays.asList(-1, 0, 1, 0);
                Y = Arrays.asList(0, -1, 0, 1);
                break;
            case BISHOP:
                X = Arrays.asList(-1, -1, 1, 1);
                Y = Arrays.asList(-1, 1, -1, 1);
                break;
            default:
                throw new IllegalArgumentException("Invalid piece type: " + type);
        }
        return new MoveStrategy(X, Y, canMoveOneStepOnly);
    }
}