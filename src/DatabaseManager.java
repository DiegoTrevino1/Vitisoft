import java.sql.*;
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

    /**
     * Run some tests to make sure it works
     */
    public static void test() {
        Account user = new Account("deano", "1234");
        insertUser(user.getUsername(), user.getPassword(), LocalDateTime.now(), "deano@dean.com", "Dean", "Yockey");
        user = getUser("deano");
        insertEmergency(user.getUsername(), LocalDateTime.now(), "", "FIRE!", "123 Fire Street", "fire", 10);
        // need a simple way to get the auto-generated ID of that inserted emergency
        insertEmergencyUpdate(1, LocalDateTime.now(), "MORE FIRE!");
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
        try {
            String query = String.format("""
                INSERT INTO users 
                VALUES
                ("%s", "%s", "%s", "%s", "%s", "%s")
                ;
                """,
            userName, passwordHash, lastLogin.toString(), email,
            firstName, lastName
            );
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        }
        catch(Exception e) {
            System.out.println(e.toString());
        }
            
   }

   public static Account getUser(String userName) {
        
        try {
            String query = String.format("""
                    SELECT *
                    FROM users
                    WHERE userName = "%s"
                    ;
                    """, userName);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            result.next();
            
            return new Account(userName, result.getString("userPasswordHash"));
        }
        catch(Exception e) {
            System.out.println(e.toString());
            return null;
        }
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

    public static void insertEmergency(Emergency emergency) {
        
        try {
            String query = String.format("""
                INSERT INTO emergencies 
                VALUES
                (NULL, "%s", "%s", "%s", "%s", "%s", "%s", %b, %d)
                ;
                """,
            emergency.userName, emergency.receivedTime.toString(), emergency.callerID, emergency.details,
            emergency.address, emergency.type, emergency.isActive, emergency.priority
            );
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        }
        catch(Exception e) {
            System.out.println(e.toString());
        }
    }

    // we need some kind of emergency object to return

    /*
    CREATE TABLE emergencyUpdate (
    emergencyID int NOT NULL,
    time datetime NOT NULL,
    description varchar(100) NOT NULL,

    PRIMARY KEY (emergencyID,time),
    CONSTRAINT update_fk_emergency FOREIGN KEY (emergencyID) REFERENCES emergencies (emergencyID)
    ) ENGINE=InnoDB;

     */

    public static void insertEmergencyUpdate(EmergencyUpdate emergencyUpdate) {
        try {
            String query = String.format("""
                INSERT INTO emergencyUpdate 
                VALUES
                (%d, "%s", "%s")
            ;
            """,
            emergencyUpdate.emergencyID, emergencyUpdate.time.toString(), emergencyUpdate.description
            );
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        }
        catch(Exception e) {
            System.out.println(e.toString());
        }
    }

    // we need some kind of emergencyUpdate object to return

}
