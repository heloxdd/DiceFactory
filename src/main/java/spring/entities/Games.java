package spring.entities;
import jakarta.persistence.*;
import java.time.*;

@Entity
@Table(name = "games")
public class Games {
    @Id
    private Integer id;
    private Integer p1ID;
    private Integer p2ID;
    private String p1Dice;
    private String p2Dice;
    private LocalDateTime date;
    private Integer winner;

    public int getID() {
        return id;
    }
    public int getP1ID() {
        return p1ID;
    }
    public int getP2ID() {
        return p2ID;
    }
    public void setP1ID(int newVal) {
        p1ID = newVal;
    }
    public void setP2ID(int newVal) {
        p2ID = newVal;
    }
    public String getP1Dice() {
        return p1Dice;
    }
    public void setP1Dice(String newVal) {
        p1Dice = newVal;
    }
    public String getP2Dice() {
        return p2Dice;
    }
    public void setP2Dice(String newVal) {
        p2Dice = newVal;
    }
    public int getWinner() {
        return winner;
    }
    public void setWinner(int newVal) {
        winner = newVal;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime newVal) {
        date = newVal;
    }
}
