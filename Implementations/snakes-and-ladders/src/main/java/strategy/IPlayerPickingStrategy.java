package strategy;

public interface IPlayerPickingStrategy {
    int getFirstPlayer();
    int getNextPlayer(int curPlayer, int totalPlayers);
}
