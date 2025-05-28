/*
 * @author Ian Seymour
 * @date 5/27/2025
 * @file LoginManager.java
 * @version 0.2
 * 
 * LoginManager class stores a hash map of all accounts currently
 * created. The class has methods to login, print the stored accounts,
 * add new accounts, and remove accounts.
 */

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public class LoginManager {

    // Database connection necessary to query accounts
    DatabaseManager db;

    // Currently logged in account
    String currentAccount = null;

    /*
     * @description constructor for LoginManager
     * @param HashMap containing all accounts currently stored
     */
    public LoginManager (DatabaseManager db) {
        // check the connection
        if (db == null) {
            System.out.println("ERROR: DatabaseManager is null.");
        }
        
        this.db = db;
    }

    /*
     * @description method to log into an account by checking its username
     * and password.
     * @param String username to be checked
     * @param String password to be checked (in plain text)
     * @return boolean if account was successfully logged in
     */
    public boolean logIn(String username, String password) {

        db.connect();

        // check if username is valid
        if (db.getUser(username) != null) {

            // check if password is valid
            if (checkPassword(db.getUser())) {
                
                currentAccount = username; // set currently logged in account

                // update account's last login date/time
                String logTime = ZonedDateTime.now().toString();

                db.updateLastLogin(username, logTime);

                System.out.println(username + " logged in successfully.");

                return true;

            } else {
                System.out.println("Incorrect password for user : " + username);
                return false;
            }
        } else {
            System.out.println("Username not found.");
            return false;
        }
    }

    /*
     * @description method to log out the current user.
     */
    public void logOut() {
        currentAccount = null;
    }

    /*
     * @description method to add an account
     * @param Account to be added
     */
    public void addAccount (Account account) {

        db.connect();

        String lastLogin = ZonedDateTime.now().toString();

        db.insertUser(account.getUsername(), account.getPassword(), lastLogin, account.getEmail(), account.getFirstName(), account.getLastName());
    }

    /*
     * @description method to remove an account
     * @param String username to be removed from accounts
     */
    public void removeAccount (String username) {
        db.connect();

        db.removeAccount(username);
    }

    public String getCurrentAccount () {
        return currentAccount;
    }

    /*
     * @description method to print all currently stored accounts
     *
    public printAccounts () {
        for (String key : accounts.keySet()) {
            System.out.println(key);
        }
    }
    */
}
