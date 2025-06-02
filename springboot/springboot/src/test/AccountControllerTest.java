package com.vitisoft.backend.test;

import com.vitisoft.backend.db.DatabaseManager;
import com.vitisoft.backend.model.Account;
import com.vitisoft.backend.service.LoginManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link AccountController} using mocked dependencies.
 * These tests ensure the login and signup API endpoints behave correctly
 * under various conditions.
 */
public class AccountControllerTest {

    private AccountController controller;
    private LoginManager mockLoginManager;
    private DatabaseManager mockDatabaseManager;

    @BeforeEach
    public void setUp() {
        mockLoginManager = mock(LoginManager.class);
        mockDatabaseManager = mock(DatabaseManager.class);

        // Injecting mocked login manager using a subclass of AccountController
        controller = new AccountController() {
            {
                this.loginManager = mockLoginManager;
            }
        };
    }

    /**
     * Tests successful login.
     */
    @Test
    public void testLoginSuccess() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "testUser");
        credentials.put("password", "testPass");

        when(mockLoginManager.logIn("testUser", "testPass")).thenReturn(true);

        ResponseEntity<String> response = controller.login(credentials);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Login successful", response.getBody());
    }

    /**
     * Tests failed login with invalid credentials.
     */
    @Test
    public void testLoginFailure() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "testUser");
        credentials.put("password", "wrongPass");

        when(mockLoginManager.logIn("testUser", "wrongPass")).thenReturn(false);

        ResponseEntity<String> response = controller.login(credentials);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials", response.getBody());
    }

    /**
     * Tests signup when the username already exists.
     */
    @Test
    public void testSignupUsernameExists() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "existingUser");
        credentials.put("password", "pass123");

        // Simulate existing account
        Account existing = new Account("existingUser", "", "", "", "");
        DatabaseManager.insertUser("existingUser", "pass123", "", "", "");

        ResponseEntity<String> response = controller.signup(credentials);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Username already exists.", response.getBody());
    }

    /**
     * Tests successful signup with new user.
     */
    @Test
    public void testSignupSuccess() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "newUser");
        credentials.put("password", "pass123");

        ResponseEntity<String> response = controller.signup(credentials);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Account created successfully.", response.getBody());
    }
}
