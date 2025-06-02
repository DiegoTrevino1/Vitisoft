package com.vitisoft.backend.db;

import java.sql.*;
import java.time.*;
import java.util.ArrayList;
import com.vitisoft.backend.model.Account;
import com.vitisoft.backend.model.Emergency;
import com.vitisoft.backend.model.EmergencyUpdate;
import java.time.format.DateTimeFormatter;

/**
 * Class for managing database input and output
 */
public class DatabaseManager {

    /**
     * The url to find the database (local MySQL database)
     */
    public static String dburl = "jdbc:mysql://localhost:3306/expeditedEmergencyDB";
    /**
     * Username for connecting to the database
     */
    public static String dbUserName = "bigBoss";
    /**
     * Password for connecting to the database
     */
    public static String dbPassword = "123456";

    /**
     * The connection to the database
     */
    public static Connection connection = null;

    /**
     * Method for initializing the database connection
     */
    public static void connect() {
        try {
            connection = DriverManager.getConnection(dburl, dbUserName, dbPassword);
            System.out.println("Connection successful");
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    /**
     * Method for testing database functionality
     */
    public static void test() {
        Account user = new Account("deano", "123456", "deano@dean.com", "Dean", "Yockey");
        insertUser(user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName());
        user = getUser("deano");
        if (user != null) {
            Emergency emergency = new Emergency(user.getUsername(), LocalDateTime.now(), "", "Test!", "123 Test Street",
                    "test", true, 11);
            insertEmergency(emergency);

            EmergencyUpdate emergencyUpdate = new EmergencyUpdate(emergency.id, LocalDateTime.now(), "More fire!");
            insertEmergencyUpdate(emergencyUpdate);

            ArrayList<EmergencyUpdate> list = getEmergencyUpdates(1);
        }
    }

    /**
     * Method for inserting a new user into the database
     * @param userName The user's name
     * @param passwordHash The hash of the user's password
     * @param email The user's email address
     * @param firstName The user's first name
     * @param lastName The user's last name
     */
    public static void insertUser(String userName, String passwordHash, String email, String firstName,
            String lastName) {
        try {
            String query = "INSERT INTO users (userName, userPasswordHash, userEmail, userFirstName, userLastName) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
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
     * A method for generating an Account object from data in the database
     * @param userName The user's unique username
     * @return An Account object
     */
    public static Account getUser(String userName) {
        try {
            String query = "SELECT * FROM users WHERE userName = ?";
            PreparedStatement statement = connection.prepareStatement(query);
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
     * Insert's a new emergency into the database
     * @param emergency The Emergency object
     */
    public static void insertEmergency(Emergency emergency) {
        try {
            String query = "INSERT INTO emergencies (userName, receivedTime, callerID, emergencyDetails, emergencyAddress, emergencyType, isActiveEmergency, priority) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, emergency.userName);
            statement.setTimestamp(2, Timestamp.valueOf(emergency.receivedTime));
            statement.setString(3, emergency.callerID);
            statement.setString(4, emergency.details);
            statement.setString(5, emergency.address);
            statement.setString(6, emergency.type);
            statement.setInt(7, emergency.isActive ? 1 : 0);
            statement.setInt(8, emergency.priority);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                emergency.id = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Method for retrieving an emergency from the database
     * @param emergencyID The emergency's unique ID
     * @return An Emergency object
     */
    public static Emergency getEmergency(int emergencyID) {
        try {
            String query = "SELECT * FROM emergencies WHERE emergencyID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, emergencyID);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss");
                return new Emergency(emergencyID, result.getString("userName"),
                        LocalDateTime.parse(result.getString("receivedTime"), formatter),
                        result.getString("callerID"), result.getString("emergencyDetails"),
                        result.getString("emergencyAddress"), result.getString("emergencyType"),
                        result.getBoolean("isActiveEmergency"), result.getInt("priority"));
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * Insert's an emergency update into the database
     * @param emergencyUpdate The EmergencyUpdate object
     */
    public static void insertEmergencyUpdate(EmergencyUpdate emergencyUpdate) {
        try {
            String query = "INSERT INTO emergencyUpdate (emergencyID, time, description) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, emergencyUpdate.emergencyID);
            statement.setTimestamp(2, Timestamp.valueOf(emergencyUpdate.time));
            statement.setString(3, emergencyUpdate.description);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Gets all emergency updates for the specified emergency
     * @param emergencyID The emergency's unique ID
     * @return An ArrayList of emergency updates for the specified emergency
     */
    public static ArrayList<EmergencyUpdate> getEmergencyUpdates(int emergencyID) {
        ArrayList<EmergencyUpdate> list = new ArrayList<>();
        try {
            String query = "SELECT * FROM emergencyUpdate WHERE emergencyID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, emergencyID);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss");
                list.add(new EmergencyUpdate(emergencyID, LocalDateTime.parse(result.getString("time"), formatter),
                        result.getString("description")));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }

        return list;
    }

    /**
     * Removes an account from the user table
     * @param username The user's unique username
     * @return true if a user was deleted, and false if a user was not deleted
     */
    public static boolean removeAccount(String username) {
        try {
            String sql = "DELETE FROM users WHERE userName = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    /**
     * Main method. Just for testing I guess.
     * @param args Command line arguments. Never used
     */
    public static void main(String[] args) {
        connect();
        test();
    }
}