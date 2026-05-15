package spring;

import java.util.List;

public class UserRequest {
    private int id;
    private String username;

    public int getPlayerId() { return id; }
    public String getUsername() { return username; }
    public void setUsername(String newUsername) { username = newUsername; }
}
