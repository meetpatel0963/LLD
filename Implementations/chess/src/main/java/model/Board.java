package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Board {
    private final int rows;
    private final int cols;
    private final List<List<Cell>> cells;

    public String getPosition(int x, int y) {
        return "" + (char)('A' + y) + (char)('0' + (8 - x));
    }
}
