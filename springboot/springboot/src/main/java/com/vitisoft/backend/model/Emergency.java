package com.vitisoft.backend.model;

import java.time.*;

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

/**
 * Class to represent an emergency. Mirriors entries in the emergencies table in the database
 */
public class Emergency {

    /**
     * The next ID for auto-incrementing, when an emergency is created by the program.
     * Unfortunately we never check emergencies already in the database, so this doesn't actually work
     */
    public static int nextID = 1;

    /**
     * The emergency's unique ID
     */
    public int id;
    /**
     * The username of the user who created this emergency. For documentation purposes, maybe.
     */
    public String userName;
    /**
     * The time when this emergency was first received
     */
    public LocalDateTime receivedTime;
    /**
     * The caller ID of whoever called 911, if available
     */
    public String callerID;
    /**
     * A description of the emergency
     */
    public String details;
    /**
     * The address where the emergency is happening
     */
    public String address;
    /**
     * The type of emergency it is
     */
    public String type;
    /**
     * Whether this emergency is currently active, or if it's been resolved
     */
    public boolean isActive;
    /**
     * The priority of the emergency
     */
    public int priority;

    /**
     * Create a new Emergency object with the ID chosen automatically
     * @param userName The username of the user who created this emergency
     * @param receivedTime The time when this emergency was first received
     * @param callerID The caller ID of whoever called 911, if available
     * @param details The address where the emergency is happening
     * @param address The type of emergency it is
     * @param type Whether this emergency is currently active, or if it's been resolved
     * @param isActive Whether this emergency is currently active, or if it's been resolved
     * @param priority The priority of the emergency
     */
    public Emergency(String userName, LocalDateTime receivedTime,
            String callerID, String details, String address, String type, boolean isActive, int priority) {
        this.id = nextID;
        nextID++;
        this.userName = userName;
        this.receivedTime = receivedTime;
        this.callerID = callerID;
        this.details = details;
        this.address = address;
        this.type = type;
        this.isActive = isActive;
        this.priority = priority;
    }

    /**
     * Create a new Emergency object
     * @param id The emergeny's unique ID
     * @param userName The username of the user who created this emergency
     * @param receivedTime The time when this emergency was first received
     * @param callerID The caller ID of whoever called 911, if available
     * @param details The address where the emergency is happening
     * @param address The type of emergency it is
     * @param type Whether this emergency is currently active, or if it's been resolved
     * @param isActive Whether this emergency is currently active, or if it's been resolved
     * @param priority The priority of the emergency
     */
    public Emergency(int id, String userName, LocalDateTime receivedTime,
            String callerID, String details, String address, String type, boolean isActive, int priority) {
        this.id = id;
        nextID++;
        this.userName = userName;
        this.receivedTime = receivedTime;
        this.callerID = callerID;
        this.details = details;
        this.address = address;
        this.type = type;
        this.isActive = isActive;
        this.priority = priority;
    }
}

/*
 * CREATE TABLE emergencyUpdate (
 * emergencyID int NOT NULL,
 * time datetime NOT NULL,
 * description varchar(100) NOT NULL,
 * 
 * PRIMARY KEY (emergencyID,time),
 * CONSTRAINT update_fk_emergency FOREIGN KEY (emergencyID) REFERENCES
 * emergencies (emergencyID)
 * ) ENGINE=InnoDB;
 * 
 */
