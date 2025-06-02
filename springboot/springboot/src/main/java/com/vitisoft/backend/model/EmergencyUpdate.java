package com.vitisoft.backend.model;

import java.time.LocalDateTime;

public class EmergencyUpdate {
    public int emergencyID;
    public LocalDateTime time;
    public String description;

    public EmergencyUpdate(int emergencyID, LocalDateTime time, String description) {
        this.emergencyID = emergencyID;
        this.time = time;
        this.description = description;
    }
}