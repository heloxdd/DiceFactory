package spring;

import java.sql.Timestamp;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;


public class GameRequest {

    @JsonProperty private Integer player1ID;
    @JsonProperty private Integer player2ID;
    @JsonProperty private List<Integer> player1Dice;
    @JsonProperty private List<Integer> player2Dice;

    public int getP1ID() { return player1ID; }
    public int getP2ID() { return player2ID; }
    public List<Integer> getP1Dice() { return player1Dice; }
    public List<Integer> getP2Dice() { return player2Dice; }
}
