package com.vitisoft.backend.db;

import java.sql.*;
import java.time.*;
import java.util.ArrayList;
import com.vitisoft.backend.model.Account;
import com.vitisoft.backend.model.Emergency;
import com.vitisoft.backend.model.EmergencyUpdate;
import java.time.format.DateTimeFormatter;

public class DatabaseManager {

    public static String dburl = "jdbc:mysql://localhost:3306/expeditedEmergencyDB";
    public static String dbUserName = "bigBoss";
    public static String dbPassword = "123456";

    public static Connection connection = null;

    public static void connect() {
        try {
            connection = DriverManager.getConnection(dburl, dbUserName, dbPassword);
            System.out.println("Connection successful");
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public static void test() {
        Account user = new Account("deano", "123456", "deano@dean.com", "Dean", "Yockey");
        insertUser(user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName());
        user = getUser("deano");
        if (user != null) {
            Emergency emergency = new Emergency(user.getUsername(), LocalDateTime.now(), "", "FIRE!", "123 Fire Street",
                    "fire", true, 10);
            insertEmergency(emergency);

            EmergencyUpdate emergencyUpdate = new EmergencyUpdate(emergency.id, LocalDateTime.now(), "More fire!");
            insertEmergencyUpdate(emergencyUpdate);
        }
    }

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

    public static ArrayList<EmergencyUpdate> getEmergencyUpdates(int emergencyID) {
        return null; // placeholder
    }

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

    public static void main(String[] args) {
        connect();
        test();
    }
}