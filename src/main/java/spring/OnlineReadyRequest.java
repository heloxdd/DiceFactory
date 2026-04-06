package spring;

import java.util.List;

public class OnlineReadyRequest {
    private int gameId;
    private int playerId;
    private List<Integer> playerDice;

    public int getGameId() {return gameId;}
    public int getPlayerId() {return playerId;}
    public List<Integer> getPlayerDice() {return playerDice;}
}
