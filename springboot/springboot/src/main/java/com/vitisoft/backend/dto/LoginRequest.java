package com.vitisoft.backend.dto;

// Named dto because this is a Data Transfer Object. 
// It is used to transfer data between the client and server.
public class LoginRequest {
    private String username;
    private String password;

    // Getters and setters (or use Lombok for brevity)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
