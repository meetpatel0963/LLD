package model;

import exception.InvalidOperationException;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class Board {
    private final int rows;
    private final int cols;
    private final Map<Integer,Integer> moveTo;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.moveTo = new HashMap<>();
    }

    public void addSnakeOrLadder(int from, int to) {
        if (moveTo.containsKey(from)) {
            throw new InvalidOperationException("Position " + from + " already contains a snake or a ladder.");
        }
        moveTo.put(from, to);
    }

    public int getTarget() {
        return rows * cols;
    }

    public void move(Player player) {
        if (moveTo.containsKey(player.getPosition())) {
            int from = player.getPosition();
            int to = moveTo.get(player.getPosition());
            System.out.println("Player " + player.getId() + " moved from " + from + " to " + to + " due to " + ((from >= to) ? "snake." : "ladder."));
            player.setPosition(to);
        }
    }
}
