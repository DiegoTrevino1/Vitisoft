import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseManager {

    // please run the "create_Expedited_EmergencyDB.sql" file in MySQL workbench to create the database and tables

    // please use the following line in MySQL/MariaDB in the command line:
    // grant all privileges on *.* to 'admin'@'localhost' with grant option;

    public static String url = "jdbc:mysql://localhost:3306/expeditedEmergencyDB";
    public static String userName = "admin";
    public static String password = "password";

    public static Connection connection = null;

    public static void Connect() {
        try {
            connection = DriverManager.getConnection(url, userName, password);
            System.out.println("Connection successful");
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }


}
