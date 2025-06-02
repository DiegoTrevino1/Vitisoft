package com.vitisoft.backend.service;

import jakarta.persistence.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Entity // @Entity marks a java class as a database table
/*
 * @Table is a JPA(Java persistence API) annoataion used to customize the 
 * table name and constraints when mapping a Java class to a database.
 * If @Table isn't used it will default to a table named Account.
 */
@Table(name = "users") 

public class Account {
    @Id // Primary key to map class to database table
    /*
     * @Column is a JPA annotation giving specific control over how a field
     * in the Java class maps to a column in the database. 
     */
    @Column(name = "userName", nullable = false, length = 20)
    private String username; // Username and password

    // nullable checks if the column can be NULL, default is true. 
    @Column(name = "userPasswordHash", nullable = false, columnDefinition = "VARCHAR(255)")
    private String passHash; // using a SHA256 hash

    @Column(name = "userLastLogin", nullable = false)
    private String lastLogin; // DTG of the last login

    @Column(name = "userEmail", nullable = false, length = 50)
    private String email;

    @Column(name = "userFirstName", nullable = false, length = 50)
    private String firstName;

    @Column(name = "userLastName", nullable = false, length = 50)
    private String lastName;

    // Required no-arg constructor for JPA
    public Account() {
    }

    /*
     * @description Account constructor takes a username String and
     * a pasword provided by the user. The username is assigned
     * and the password is hashed using SHA256 and then stored
     * 
     * @param String username to allow logins and tracking of
     * accounts.
     * 
     * @param String password to be hashed then stored.
     */
    public Account(String username, String password, String email, String firstName, String lastName) {
        this.username = username;
        this.passHash = hashPassword(password);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.lastLogin = "N/A";
    }

    // ---------------- Getters ----------------
    /*
     * @description getter for username
     * 
     * @return String username
     */
    public String getUsername() {
        return username;
    }

    /*
     * @description getter for hashed password
     * 
     * @return String hashed password stored for account
     */
    public String getPassword() {
        return passHash;
    }

    /*
     * @description getter for last login time
     * 
     * @return String last login date-time-group
     */
    public String getLastLogin() {
        return lastLogin;
    }

    /*
     * @description getter for email
     * 
     * @return String email
     */
    public String getEmail() {
        return email;
    }

    /*
     * @description getter for first name
     * 
     * @return String first name
     */
    public String getFirstName() {
        return firstName;
    }

    /*
     * @description getter for last name
     * 
     * @return String last name
     */
    public String getLastName() {
        return lastName;
    }

    /*
     * @description setter for last login time
     * 
     * @param String new login time
     */
    public void setLastLogin(String loginTime) {
        lastLogin = loginTime;
    }

    /*
     * @description hashPassowrd takes in a plain text String
     * and hashes it using SHA256 and returns the encrypted
     * password to be stored.
     * 
     * @param String password plain text user defined pass
     * 
     * @return String hashed password with SHA256
     */
    private String hashPassword(String password) {
        try {
            byte[] salt = getSalt(); // get a random salt
            String hashedPass = hash(password, salt); // store hashed pass

            // return encrypted password
            return Base64.getEncoder().encodeToString(salt) + "$" + hashedPass;

        } catch (NoSuchAlgorithmException e) {
            System.out.println("ERROR : Hash algorithm failed.");
            e.printStackTrace();
            return null;
        }
    }

    /*
     * @description getSalt generates a new salt for hashing
     * 
     * @return byte[] containing the salt
     */
    private byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom rand = SecureRandom.getInstanceStrong();
        byte[] salt = new byte[16];
        rand.nextBytes(salt);
        return salt;
    }

    /*
     * @description hash takes a password string and a salt and
     * hashes the password using the provided salt.
     * 
     * @param String password in plain text
     * 
     * @param byte[] salt as a byte array
     * 
     * @return String hashed password
     */
    private String hash(String password, byte[] salt) throws NoSuchAlgorithmException {
        // get SHA256 encryption
        MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
        mDigest.update(salt); // update with salt
        // hash password
        byte[] hashedPassword = mDigest.digest(password.getBytes());

        // return hashed password encrypted
        return Base64.getEncoder().encodeToString(hashedPassword);
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

}
