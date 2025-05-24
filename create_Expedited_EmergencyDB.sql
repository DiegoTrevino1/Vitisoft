DROP DATABASE IF EXISTS expeditedEmergencyDB;
CREATE DATABASE expeditedEmergencyDB;
USE expeditedEmergencyDB;

--
-- Table structure for table users
--

CREATE TABLE users (
  userName varchar(20) NOT NULL,
  userPasswordHash varchar(255) NOT NULL,
  userLastLogin datetime NOT NULL,
  userEmail varchar(50) NOT NULL,
  userFirstName varchar(50) NOT NULL,
  userLastName varchar(50) NOT NULL,
  PRIMARY KEY (userName),
  UNIQUE KEY userID_UNIQUE (userName)
) ENGINE=InnoDB;

INSERT INTO users VALUES ('bigBoss',123456,'2025-05-12 13:08:22','bigboss@vitisoft.com','Big','Boss');

--
-- Table structure for table emergencies
--

CREATE TABLE emergencies (
  emergencyID int NOT NULL AUTO_INCREMENT,
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

--
-- Table structure for table activeVehicles
--

CREATE TABLE activeVehicles (
  vehicleID int NOT NULL,
  assignedEmergency int DEFAULT NULL,
  lastLocation point NOT NULL,
  locationTime datetime NOT NULL,
  vehicleType varchar(50) NOT NULL,
  PRIMARY KEY (vehicleID),
  KEY vehicle_fk_emergency_idx (assignedEmergency),
  CONSTRAINT vehicle_fk_emergency FOREIGN KEY (assignedEmergency) REFERENCES emergencies (emergencyID)
) ENGINE=InnoDB;

--
-- Table structure for table emergencyUpdate
--

CREATE TABLE emergencyUpdate (
  emergencyID int NOT NULL,
  time datetime NOT NULL,
  description varchar(100) NOT NULL,
  PRIMARY KEY (emergencyID,time),
  CONSTRAINT update_fk_emergency FOREIGN KEY (emergencyID) REFERENCES emergencies (emergencyID)
) ENGINE=InnoDB;
