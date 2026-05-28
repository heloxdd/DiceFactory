package spring;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRequest {

    @JsonProperty("username")
    private String username;
    private String email;
    @JsonProperty("password")
    private String password;


    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return Encryptor.encrypt(password); }

}
