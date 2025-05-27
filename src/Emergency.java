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
    public int emergencyID;
    public String userName;
    public LocalDateTime receivedTime;
    public String callerID;
    public String emergencyDetails;
    public String emergencyAddress;
    public String emergencyType;
    public boolean isActive;
    public int priority;
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

public class EmergencyUpdate {
    public int emergencyID;
    public LocalDateTime time;
    public String description;
}