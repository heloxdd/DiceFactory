package spring;

import java.sql.Timestamp;
import java.util.List;

public class GameRequest {

    private Integer id;
    private Integer player1ID;
    private Integer player2ID;
    private List<Integer> player1Dice;
    private List<Integer> player2Dice;
    private List<Integer> preGamePlayer1Dice;
    private List<Integer> preGamePlayer2Dice;
    private Integer player1Points;
    private Integer player2Points;
    private Timestamp date;
    private Integer WinnerID;

    public int getID() { return id; }
    public int getP1ID() { return player1ID; }
    public int getP2ID() { return player2ID; }
    public List<Integer> getP1Dice() { return player1Dice; }
    public List<Integer> getP2Dice() { return player2Dice; }
    public int getP1Points() { return player1Points; }
    public int getP2Points() { return player2Points; }
    public Timestamp getDate() { return date; }
    public Integer getWinnerID() { return WinnerID; }

    public List<Integer> getPreGameP1Dice() { return preGamePlayer1Dice; }
    public List<Integer> getPreGameP2Dice() { return preGamePlayer2Dice; }

}
