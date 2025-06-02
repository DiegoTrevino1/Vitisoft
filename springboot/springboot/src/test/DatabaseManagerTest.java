package com.vitisoft.backend.test;

import com.vitisoft.backend.model.Account;
import com.vitisoft.backend.model.Emergency;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link DatabaseManager} class.
 * 
 * This class verifies the correct behavior of database operations including
 * user account
 * insertion, retrieval, and emergency record management. Tests assume the
 * existence of
 * a MySQL database named `expeditedEmergencyDB` configured appropriately.
 * 
 * Note: These tests require a live connection to the MySQL database.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DatabaseManagerTest {

    private static final String TEST_USERNAME = "testuser";
    private static final String TEST_PASSWORD_HASH = "salt$hashedpassword";
    private static final String TEST_EMAIL = "testuser@example.com";
    private static final String TEST_FIRST_NAME = "Test";
    private static final String TEST_LAST_NAME = "User";

    /**
     * Tests inserting a new user into the database.
     */
    @Test
    @Order(1)
    public void testInsertUser() {
        DatabaseManager.connect();
        DatabaseManager.insertUser(TEST_USERNAME, TEST_PASSWORD_HASH, TEST_EMAIL, TEST_FIRST_NAME, TEST_LAST_NAME);

        Account user = DatabaseManager.getUser(TEST_USERNAME);
        assertNotNull(user, "User should exist after insertion.");
        assertEquals(TEST_USERNAME, user.getUsername());
        assertEquals(TEST_PASSWORD_HASH, user.getPassword());
        assertEquals(TEST_EMAIL, user.getEmail());
    }

    /**
     * Tests deleting the test user from the database.
     */
    @Test
    @Order(2)
    public void testRemoveAccount() {
        DatabaseManager.connect();
        DatabaseManager.removeAccount(TEST_USERNAME);

        Account user = DatabaseManager.getUser(TEST_USERNAME);
        assertNull(user, "User should be null after deletion.");
    }

    /**
     * Tests inserting a new emergency into the database.
     */
    @Test
    @Order(3)
    public void testInsertEmergency() {
        Emergency emergency = new Emergency();
        emergency.setUserName("dispatcher1");
        emergency.setCallerID("P123");
        emergency.setAddress("999 Test Ave");
        emergency.setDetails("Unit test emergency");
        emergency.setReceivedTime(java.time.LocalDateTime.now().toString());
        emergency.setType("Police");
        emergency.setIsActive(true);
        emergency.setPriority(3);

        DatabaseManager.insertEmergency(emergency);

        List<Emergency> list = DatabaseManager.getAllEmergencies();
        boolean found = list.stream().anyMatch(e -> e.getAddress().equals("999 Test Ave"));
        assertTrue(found, "Inserted emergency should appear in fetched list.");
    }

    /**
     * Tests retrieving all emergency records.
     */
    @Test
    @Order(4)
    public void testGetAllEmergencies() {
        List<Emergency> emergencies = DatabaseManager.getAllEmergencies();
        assertNotNull(emergencies, "Returned list should not be null.");
        assertFalse(emergencies.isEmpty(), "List should not be empty if testInsertEmergency ran first.");
    }
}
