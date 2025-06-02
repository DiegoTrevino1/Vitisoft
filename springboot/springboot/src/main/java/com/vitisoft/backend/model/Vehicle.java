package com.vitisoft.backend.model;

/**
 * Abstract base class representing a generic vehicle in the dispatch system.
 * This class provides shared attributes and methods for all vehicle types
 * including
 * PatrolCar, Ambulance, and FireEngine.
 *
 * Each vehicle is uniquely identified by its bumper number, has a designated
 * type,
 * and is associated with a driver. Subclasses may extend this class to include
 * specialized equipment or attributes relevant to their function.
 *
 * @author Diego T.
 */
public abstract class Vehicle {

    /** Unique bumper number of the vehicle. */
    private String bumperNum;

    /** Type of the vehicle (e.g., Police, Fire, Medical). */
    private String type;

    /** Driver assigned to this vehicle. */
    private String status;

    /**
     * Constructs a Vehicle object with the specified bumper number, type, and
     * driver.
     *
     * @param bumperNum the unique bumper number of the vehicle
     * @param type      the type/category of the vehicle
     * @param driver    the name or ID of the vehicle driver
     */
    public Vehicle(String bumperNum, String type, String status) {
        this.bumperNum = bumperNum;
        this.type = type;
        this.status = status;
    }

    /**
     * Returns the bumper number of the vehicle.
     *
     * @return bumper number as a String
     */
    public String getBumper() {
        return bumperNum;
    }

    /**
     * Returns the type of the vehicle.
     *
     * @return vehicle type as a String
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the current status of the vehicle.
     *
     * @return The vehicle's status as a String.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Updates the current status of the vehicle.
     *
     * @param status The new status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Displays the basic vehicle information in a formatted string.
     */
    public void displayInfo() {
        System.out.println("Bumper: " + bumperNum + ", Type: " + type + ", Status: " + status);
    }
}
