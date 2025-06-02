package com.vitisoft.backend.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import com.vitisoft.backend.db.DatabaseManager;
import com.vitisoft.backend.model.Account;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

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

public class LoginManager {

    // Database connection necessary to query accounts
    DatabaseManager db;

    // Currently logged in account
    String currentAccount = null;

    /*
     * @description constructor for LoginManager
     * 
     * @param HashMap containing all accounts currently stored
     */
    public LoginManager(DatabaseManager db) {
        // check the connection
        if (db == null) {
            System.out.println("ERROR: DatabaseManager is null.");
        }

        this.db = db;
    }

    /*
     * @description method to log into an account by checking its username
     * and password.
     * 
     * @param String username to be checked
     * 
     * @param String password to be checked (in plain text)
     * 
     * @return boolean if account was successfully logged in
     */
    public boolean logIn(String username, String password) {
        DatabaseManager.connect();
        Account account = DatabaseManager.getUser(username); // Only query once
        // check if username is valid
        if (account != null) {
            // check if password is valid
            if (checkPassword(password, account.getPassword())) {
                currentAccount = username; // set currently logged in account
                // update account's last login date/time
                String logTime = LocalDateTime.now().toString();
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
     * 
     * @param Account to be added
     */
    public void addAccount(Account account) {
        DatabaseManager.connect();
        DatabaseManager.insertUser(account.getUsername(), account.getPassword(), account.getEmail(),
                account.getFirstName(), account.getLastName());
    }

    /*
     * @description method to remove an account
     * 
     * @param String username to be removed from accounts
     */
    public void removeAccount(String username) {
        DatabaseManager.connect();
        DatabaseManager.removeAccount(username);
    }

    public String getCurrentAccount() {
        return currentAccount;
    }

    /*
     * @description checkPassword is a method to take password entered by
     * the user and check it against the stored password in its hash form
     * 
     * @param String password to be checked
     * 
     * @param String pasword stored as a hash
     * 
     * @return boolean indicating if the passwords are the same
     */
    public boolean checkPassword(String passCheck, String passStored) {
        try {
            // splits hashed password into the salt and the password
            String[] passTokens = passStored.split("\\$");

            // get salt
            byte[] salt = Base64.getDecoder().decode(passTokens[0]);
            String storedPass = passTokens[1];

            // hash the input password to check against the one stored
            String checkHash = hash(passCheck, salt);

            // compare the two hashed passwords and verify they are the same
            // return the boolean for their equality
            return storedPass.equals(checkHash);

        } catch (NoSuchAlgorithmException e) {
            System.out.println("ERROR : Hash algorithm failed.");
            e.printStackTrace();
            return false;
        }
    }

    // Add hash method for password checking
    private String hash(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword);
    }

    /*
     * @description method to print all currently stored accounts
     *
     * public printAccounts () {
     * for (String key : accounts.keySet()) {
     * System.out.println(key);
     * }
     * }
     */
}
