package com.vitisoft.backend.controller;

import com.vitisoft.backend.db.DatabaseManager;
import com.vitisoft.backend.model.Account;
import com.vitisoft.backend.service.LoginManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allow frontend to connect
public class AccountController {

    private final LoginManager loginManager;

    public AccountController() {
        DatabaseManager.connect(); // Ensure DB is initialized
        this.loginManager = new LoginManager(new DatabaseManager());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (loginManager.logIn(username, password)) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        Account existing = DatabaseManager.getUser(username);
        if (existing != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists.");
        }

        // You can later expand this to collect email, first/last name
        DatabaseManager.insertUser(username, password, "", "", "");

        return ResponseEntity.ok("Account created successfully.");
    }
}
