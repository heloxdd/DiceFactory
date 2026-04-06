package spring.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;

    public int getID() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String newVal) {
        username = newVal;
    }
}
