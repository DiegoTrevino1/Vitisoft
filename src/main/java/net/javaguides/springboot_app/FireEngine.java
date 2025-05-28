package net.javaguides.springboot_app;

import java.util.LinkedList;

/**
 * Represents a FireEngine unit in the dispatch system.
 * Extends the generic Vehicle class and includes firefighting equipment.
 */
class FireEngine extends Vehicle {

    private LinkedList<String> equipment; // List of firefighting equipment carried

    /**
     * Constructs a FireEngine with the given bumper number, type, status, and
     * equipment list.
     *
     * @param bumperNum Unique bumper number identifying the fire engine.
     * @param type      The type/category of the vehicle, should be "Fire".
     * @param status    Current operational status.
     * @param equipment List of firefighting equipment on board.
     */
    public FireEngine(String bumperNum, String type, String status, LinkedList<String> equipment) {
        super(bumperNum, type, status);
        this.equipment = equipment;
    }

    /**
     * Returns the list of firefighting equipment on board.
     *
     * @return A LinkedList of equipment names.
     */
    public LinkedList<String> getEquipment() {
        return equipment;
    }

    /**
     * Displays fire engine-specific information including equipment.
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Equipment: " + equipment);
    }
}
