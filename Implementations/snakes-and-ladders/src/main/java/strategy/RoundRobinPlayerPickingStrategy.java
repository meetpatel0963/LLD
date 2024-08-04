package strategy;

public class RoundRobinPlayerPickingStrategy implements IPlayerPickingStrategy {
    @Override
    public int getFirstPlayer() {
        return 0;
    }

    @Override
    public int getNextPlayer(int curPlayer, int totalPlayers) {
        return (curPlayer + 1) % totalPlayers;
    }
}
