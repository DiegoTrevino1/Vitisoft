package net.javaguides.springboot_app;

import java.util.LinkedList;

/**
 * Represents an Ambulance unit in the dispatch system.
 * Extends the generic Vehicle class and includes equipment.
 */
class Ambulance extends Vehicle {

    private LinkedList<String> equipment;  // List of medical equipment carried

    /**
     * Constructs an Ambulance with the given bumper number, type, status, and equipment list.
     *
     * @param bumperNum Unique bumper number identifying the ambulance.
     * @param type      The type/category of the vehicle, should be "Medical".
     * @param status    Current operational status.
     * @param equipment List of medical equipment on board.
     */
    public Ambulance(String bumperNum, String type, String status, LinkedList<String> equipment) {
        super(bumperNum, type, status);
        this.equipment = equipment;
    }

    /**
     * Returns the list of medical equipment on board.
     *
     * @return A LinkedList of equipment names.
     */
    public LinkedList<String> getEquipment() {
        return equipment;
    }

    /**
     * Displays ambulance-specific information including equipment.
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Equipment: " + equipment);
    }
}

