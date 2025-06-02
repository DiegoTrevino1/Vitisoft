package com.vitisoft.backend.controller;

import com.vitisoft.backend.db.DatabaseManager;
import com.vitisoft.backend.model.Account;
import com.vitisoft.backend.service.LoginManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST controller that handles account-related operations such as login and
 * signup.
 * <p>
 * All endpoints are prefixed with "/api" and allow CORS requests from any
 * origin.
 * REST API behavior:
 * POST /api/login: Authenticates a user with username and password.
 * POST /api/signup: Registers a new user account if the username is not
 * already taken.
 * * This controller uses the {@link LoginManager} to handle authentication
 * and user management.
 * </p>
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allow frontend to connect
public class AccountController {

    private final LoginManager loginManager;

    /**
     * Constructs the AccountController and initializes the LoginManager.
     * Establishes a connection to the database to ensure backend functionality.
     */
    public AccountController() {
        DatabaseManager.connect(); // Ensure DB is initialized
        this.loginManager = new LoginManager(new DatabaseManager());
    }

    /**
     * Authenticates a user based on submitted login credentials.
     *
     * @param credentials A map containing the "username" and "password" from the
     *                    request body.
     * @return HTTP 200 OK with success message if credentials are valid;
     *         otherwise, HTTP 401 Unauthorized with failure message.
     */
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

    /**
     * Registers a new user account if the username is not already taken.
     *
     * @param credentials A map containing the "username" and "password" from the
     *                    request body.
     * @return HTTP 200 OK if account is created successfully;
     *         HTTP 409 Conflict if username already exists.
     */
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
