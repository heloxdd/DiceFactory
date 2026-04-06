package spring.entities;
import jakarta.persistence.*;

@Entity
@Table(name="contacts")
public class Contact {
    @Id
    private Integer userID;
    private String email;
    private String phoneNumber;

    public int getId() {
        return userID;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String newVal) {
        email = newVal;
    }
    public String getNumber() {
        return phoneNumber;
    }
    public void setNumber(String newVal) {
        phoneNumber = newVal;
    }
}
