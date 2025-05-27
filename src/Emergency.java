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

public class Emergency {

    public static int nextID = 1;

    public int id;
    public String userName;
    public LocalDateTime receivedTime;
    public String callerID;
    public String details;
    public String address;
    public String type;
    public boolean isActive;
    public int priority;

    public Emergency(String userName, LocalDateTime receivedTime, 
            String callerID, String details, String address, String type, boolean isActive, int priority) {
        this.id = nextID;
        nextID ++;
        this.userName = userName;
        this.receivedTime = receivedTime;
        this.callerID = callerID;
        this.details = details;
        this.address = address;
        this.type = type;
        this.isActive = isActive;
        this.priority = priority;
    }

    public Emergency(int id, String userName, LocalDateTime receivedTime, 
            String callerID, String details, String address, String type, boolean isActive, int priority) {
        this.id = id;
        nextID ++;
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
CREATE TABLE emergencyUpdate (
emergencyID int NOT NULL,
time datetime NOT NULL,
description varchar(100) NOT NULL,

PRIMARY KEY (emergencyID,time),
CONSTRAINT update_fk_emergency FOREIGN KEY (emergencyID) REFERENCES emergencies (emergencyID)
) ENGINE=InnoDB;

    */

class EmergencyUpdate {
    public int emergencyID;
    public LocalDateTime time;
    public String description;

    public EmergencyUpdate(int emergencyID, LocalDateTime time, String description) {
        this.emergencyID = emergencyID;
        this.time = time;
        this.description = description;
    }
}