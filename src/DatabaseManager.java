import java.sql.Connection;
import java.sql.DriverManager;
import java.time.*;

public class DatabaseManager {

    // please run the "create_Expedited_EmergencyDB.sql" file in MySQL workbench to create the database and tables

    // please use the following line in MySQL/MariaDB in the command line:
    // grant all privileges on *.* to 'admin'@'localhost' with grant option;

    public static String dburl = "jdbc:mysql://localhost:3306/expeditedEmergencyDB";
    public static String dbUserName = "admin";
    public static String dbPassword = "password";

    public static Connection connection = null;

    public static void connect() {
        try {
            connection = DriverManager.getConnection(dburl, dbUserName, dbPassword);
            System.out.println("Connection successful");
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    /*
    CREATE TABLE users (
    userName varchar(20) NOT NULL,
    userPasswordHash varchar NOT NULL,
    userLastLogin datetime NOT NULL,
    userEmail varchar(50) NOT NULL,
    userFirstName varchar(50) NOT NULL,
    userLastName varchar(50) NOT NULL,
    PRIMARY KEY (userName),
    UNIQUE KEY userID_UNIQUE (userName)
    ) ENGINE=InnoDB;
    */
   public static void insertUser(String userName, String passwordHash, LocalDateTime lastLogin,
        String email, String firstName, String lastName) {
            // TODO
   }

   public static Account getUser(String userName) {
        // TODO
        return null;
   }


    /*
    CREATE TABLE emergencies (
    emergencyID int NOT NULL,
    userName varchar(20) NOT NULL,
    receivedTime datetime NOT NULL,
    callerID char(10) DEFAULT NULL,
    emergencyDetails varchar(500) NOT NULL,
    emergencyAddress varchar(100) NOT NULL,
    emergencyType varchar(50) NOT NULL,
    isActiveEmergency tinyint NOT NULL,
    priority int NOT NULL,

    PRIMARY KEY (emergencyID),
    KEY emergency_fk_user_idx (userName),
    CONSTRAINT emergency_fk_user FOREIGN KEY (userName) REFERENCES users (userName)
    ) ENGINE=InnoDB;
    */

    public static void insertEmergency(int emergencyID, String userName, LocalDateTime receivedTime,
            String callerID, String emergencyDetails, String emergencyAddress, 
            String emergencyType, int priority) {
        
        String query = String.format("""INSERT INTO emergencies 
        VALUES
        (%d, "%s", "%s", "%s", "%s", "%s", "%s", %d)
        ;""",
        emergencyID, userName, receivedTime.toString(), callerID, emergencyDetails,
        emergencyAddress, emergencyType, priority
        );
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }

    /*
    CREATE TABLE emergencyUpdate (
    emergencyID int NOT NULL,
    time datetime NOT NULL,
    description varchar(100) NOT NULL,

    PRIMARY KEY (emergencyID,time),
    CONSTRAINT update_fk_emergency FOREIGN KEY (emergencyID) REFERENCES emergencies (emergencyID)
    ) ENGINE=InnoDB;

     */

    public static void insertEmergencyUpdate(int emergencyID, LocalDateTime time, String description) {
        String query = String.format("""INSERT INTO emergencyUpdate 
        VALUES
        (%d, "%s", "%s")
        ;""",
        emergencyID, time.toString(), description
        );
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }

}
