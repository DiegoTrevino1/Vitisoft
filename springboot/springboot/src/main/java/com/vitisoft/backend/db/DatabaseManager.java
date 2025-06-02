package com.vitisoft.backend.db;

import java.sql.*;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import com.vitisoft.backend.model.Account;
import com.vitisoft.backend.model.Emergency;
import com.vitisoft.backend.model.EmergencyUpdate;
import java.time.format.DateTimeFormatter;

/**
 * Provides static utility methods to interact with the expeditedEmergencyDB
 * MySQL database,
 * including inserting and retrieving users, emergencies, and emergency updates.
 * DB queries and inserts
 */
public class DatabaseManager {

    public static String dburl = "jdbc:mysql://localhost:3306/expeditedEmergencyDB";
    public static String dbUserName = "bigBoss";
    public static String dbPassword = "123456";

    /**
     * Establishes and returns a connection to the database.
     * 
     * @return Connection object to the database
     * @throws SQLException if the connection fails
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dburl, dbUserName, dbPassword);
    }

    /**
     * Tests the database connection.
     */
    public static void connect() {
        try (Connection conn = getConnection()) {
            System.out.println("Connection successful");
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    /**
     * Test method that inserts a sample user, emergency, and update.
     */
    public static void test() {
        Account user = new Account("deano", "123456", "deano@dean.com", "Dean", "Yockey");
        insertUser(user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName());
        user = getUser("deano");
        if (user != null) {
            Emergency emergency = new Emergency(user.getUsername(), OffsetDateTime.now(), "", "FIRE!",
                    "123 Fire Street",
                    "fire", true, 10);
            insertEmergency(emergency);

            EmergencyUpdate emergencyUpdate = new EmergencyUpdate(emergency.id, LocalDateTime.now(), "More fire!");
            insertEmergencyUpdate(emergencyUpdate);
        }
    }

    /**
     * Inserts a new user into the users table.
     * 
     * @param userName     the username
     * @param passwordHash the hashed password
     * @param email        the user's email
     * @param firstName    the user's first name
     * @param lastName     the user's last name
     */
    public static void insertUser(String userName, String passwordHash, String email, String firstName,
            String lastName) {
        try (Connection conn = getConnection()) {
            String query = "INSERT INTO users (userName, userPasswordHash, userEmail, userFirstName, userLastName) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, userName);
            statement.setString(2, passwordHash);
            statement.setString(3, email);
            statement.setString(4, firstName);
            statement.setString(5, lastName);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Retrieves a user account based on the username.
     * 
     * @param userName the username to search for
     * @return an Account object or null if not found
     */
    public static Account getUser(String userName) {
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM users WHERE userName = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, userName);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return new Account(
                        result.getString("userName"),
                        result.getString("userPasswordHash"),
                        result.getString("userEmail"),
                        result.getString("userFirstName"),
                        result.getString("userLastName"));
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * Inserts a new emergency into the emergencies table.
     * 
     * @param e the Emergency object to insert
     */
    public static void insertEmergency(Emergency e) {
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO emergencies (userName, receivedTime, callerID, emergencyDetails, emergencyAddress, emergencyType, isActiveEmergency, priority) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, e.userName);
            statement.setTimestamp(2, Timestamp.from(e.receivedTime.toInstant()));
            statement.setString(3, e.callerID);
            statement.setString(4, e.details);
            statement.setString(5, e.address);
            statement.setString(6, e.type);
            statement.setBoolean(7, e.isActive);
            statement.setInt(8, e.priority);

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Retrieves an emergency by its ID.
     * 
     * @param emergencyID the ID of the emergency
     * @return an Emergency object or null if not found
     */
    public static Emergency getEmergency(int emergencyID) {
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM emergencies WHERE emergencyID = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, emergencyID);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return new Emergency(
                        emergencyID,
                        result.getString("userName"),
                        result.getTimestamp("receivedTime").toInstant().atOffset(ZoneOffset.UTC),
                        result.getString("callerID"),
                        result.getString("emergencyDetails"),
                        result.getString("emergencyAddress"),
                        result.getString("emergencyType"),
                        result.getBoolean("isActiveEmergency"),
                        result.getInt("priority"));
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * Inserts an emergency update into the emergencyUpdate table.
     * 
     * @param emergencyUpdate the EmergencyUpdate object to insert
     */
    public static void insertEmergencyUpdate(EmergencyUpdate emergencyUpdate) {
        try (Connection conn = getConnection()) {
            String query = "INSERT INTO emergencyUpdate (emergencyID, time, description) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, emergencyUpdate.emergencyID);
            statement.setTimestamp(2, Timestamp.valueOf(emergencyUpdate.time));
            statement.setString(3, emergencyUpdate.description);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Placeholder for retrieving emergency updates.
     * 
     * @param emergencyID the emergency ID
     * @return null (to be implemented)
     */
    public static ArrayList<EmergencyUpdate> getEmergencyUpdates(int emergencyID) {
        return null; // placeholder for now
    }

    /**
     * Deletes a user account by username.
     * 
     * @param username the username to remove
     * @return true if deletion was successful, false otherwise
     */
    public static boolean removeAccount(String username) {
        try (Connection conn = getConnection()) {
            String sql = "DELETE FROM users WHERE userName = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    /**
     * Retrieves all emergencies from the database.
     * 
     * @return list of Emergency objects
     */
    public static List<Emergency> getAllEmergencies() {
        List<Emergency> list = new ArrayList<>();

        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM emergencies ORDER BY receivedTime DESC";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Emergency e = new Emergency(
                        rs.getInt("emergencyID"),
                        rs.getString("userName"),
                        rs.getTimestamp("receivedTime").toInstant().atOffset(ZoneOffset.UTC),
                        rs.getString("callerID"),
                        rs.getString("emergencyDetails"),
                        rs.getString("emergencyAddress"),
                        rs.getString("emergencyType"),
                        rs.getBoolean("isActiveEmergency"),
                        rs.getInt("priority"));
                list.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    /**
     * Main method for testing the database connection and sample operations.
     * 
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        connect();
        test();
    }
}
