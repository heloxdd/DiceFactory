package spring.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "curgames")
public class CurGames {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "p1id")
    private Integer p1ID;
    @Column(name = "p2id")
    private Integer p2ID;
    @Column
    private String p1Dice;
    @Column
    private String p2Dice;

    public int getID() {
        return id;
    }
    public int getP1ID() {
        return p1ID;
    }
    public int getP2id() {
        return p2ID;
    }
    public void setP1ID(int newVal) {
        p1ID = newVal;
    }
    public void setP2id(int newVal) {
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
}
