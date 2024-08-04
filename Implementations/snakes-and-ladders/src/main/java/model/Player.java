package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Player {
    @Getter private final String id;
    private final String name;
    @Getter @Setter private int position;
    private final Board board;

    public void move(int steps) {
        if (steps > board.getTarget() - position) {
            return;
        }

        int from = position;
        position += steps;
        int to = position;
        System.out.println("Player " + id + " moved from " + from + " to " + to);

        board.move(this);
    }

    public boolean isWinner() {
        return position == board.getTarget();
    }
}
