package spring;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRequest {
    @JsonProperty("id")
    private Integer id;
    private String username;
    private String email;
    @JsonProperty("password")
    private String password = "";
    private Boolean isAdmin = false;

    public int getPlayerId() { return id; }
    public boolean getIsAdmin() { return isAdmin; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return Encryptor.encrypt(password); }

    public String toString(){
        return this.id +"";
    }
}
