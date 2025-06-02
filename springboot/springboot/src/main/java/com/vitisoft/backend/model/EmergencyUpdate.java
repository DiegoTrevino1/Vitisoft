package com.vitisoft.backend.model;

import java.time.LocalDateTime;

/**
 * Class for updates to an emergency. Mirriors entries in the emergencyUpdate table in the database
 */
public class EmergencyUpdate {
    /**
     * The unique ID of the emergency this maps to. Both a foreign and primary key
     */
    public int emergencyID;
    /**
     * The time when this update occurs. Primary key
     */
    public LocalDateTime time;
    /**
     * A description of what is happening or changing
     */
    public String description;

    /**
     * Creates a new EmergencyUpdate object
     * @param emergencyID The unique ID of the emergency this maps to
     * @param time The time when this update occurs
     * @param description A description of what is happening or changing
     */
    public EmergencyUpdate(int emergencyID, LocalDateTime time, String description) {
        this.emergencyID = emergencyID;
        this.time = time;
        this.description = description;
    }
}