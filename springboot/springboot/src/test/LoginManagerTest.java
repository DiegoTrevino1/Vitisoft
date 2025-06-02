package com.vitisoft.backend.test;

import com.vitisoft.backend.db.DatabaseManager;
import com.vitisoft.backend.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for the {@link LoginManager} class.
 * <p>
 * This class verifies the core login functionality, including:
 * <ul>
 * <li>Valid login with correct credentials</li>
 * <li>Login failure with incorrect password</li>
 * <li>Login failure for non-existent users</li>
 * <li>Password hashing and verification logic</li>
 * </ul>
 * </p>
 */
public class LoginManagerTest {

    private LoginManager loginManager;

    /**
     * Sets up a new LoginManager instance before each test.
     * Uses a test database or mock for DatabaseManager.
     */
    @BeforeEach
    public void setUp() {
        // Replace with a mock or a test-friendly DatabaseManager implementation
        loginManager = new LoginManager(new DatabaseManager());
    }

    /**
     * Tests that a valid username and password allow successful login.
     */
    @Test
    public void testValidLogin() {
        String username = "testuser";
        String password = "password123";

        // Assume this user already exists in the test DB
        boolean result = loginManager.logIn(username, password);
        assertTrue(result, "Valid login should succeed");
    }

    /**
     * Tests that login fails with an incorrect password.
     */
    @Test
    public void testInvalidPassword() {
        String username = "testuser";
        String password = "wrongpassword";

        boolean result = loginManager.logIn(username, password);
        assertFalse(result, "Login should fail with incorrect password");
    }

    /**
     * Tests that login fails for a non-existent user.
     */
    @Test
    public void testUnknownUser() {
        String username = "nonexistentuser";
        String password = "somepassword";

        boolean result = loginManager.logIn(username, password);
        assertFalse(result, "Login should fail for a non-existent user");
    }

    /**
     * Tests the password hash and comparison logic directly.
     */
    @Test
    public void testPasswordHashVerification() {
        String rawPassword = "securePass123";
        String storedHash = DatabaseManager.hashPassword(rawPassword); // Custom helper method
        boolean match = loginManager.checkPassword(rawPassword, storedHash);

        assertTrue(match, "Password hash should match original password");
    }
}
