/*
 * @author Ian Seymour
 * @date 5/18/2025
 * @file LoginManager.java
 * @version 0.1
 * 
 * LoginManager class stores a hash map of all accounts currently
 * created. The class has methods to login, print the stored accounts,
 * add new accounts, and remove accounts.
 */

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public class LoginManager {

    // All stored accounts
    HashMap<String, Account> accounts;

    // Currently logged in account
    String currentAccount = null;

    /*
     * @description constructor for LoginManager
     * @param HashMap containing all accounts currently stored
     */
    public LoginManager (HashMap accounts) {
        this.accounts = accounts;
    }

    /*
     * @description method to log into an account by checking its username
     * and password.
     * @param String username to be checked
     * @param String password to be checked (in plain text)
     * @return boolean if account was successfully logged in
     */
    public boolean logIn(String username, String password) {

        // check if username is valid
        if (accounts.containsKey(username)) {

            // check if password is valid
            if (accounts.get(username).checkPassword(accounts.get(username.getPassword()))) {
                
                currentAccount = username; // set currently logged in account

                // update account's last login date/time
                accounts.get(username).setLastLogin(ZonedDateTime.now());

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
     * @param String username to log out.
     */
    public logOut(String username) {
        currentAccount = null;
    }

    /*
     * @description method to add an account
     * @param Account to be added
     */
    public addAccount (Account account) {
        accounts.put(account.getUsername(), account);
    }

    /*
     * @description method to remove an account
     * @param String username to be removed from accounts
     */
    public removeAccount (String username) {
        accounts.remove(username);
    }

    public String getCurrentAccount () {
        return currentAccount;
    }

    /*
     * @description method to print all currently stored accounts
     */
    public printAccounts () {
        for (String key : accounts.keySet()) {
            System.out.println(key);
        }
    }
}