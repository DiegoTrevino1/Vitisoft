package net.javaguides.springboot_app;

import java.util.LinkedList;

/**
 * Represents a PatrolCar unit in the dispatch system.
 * Extends the generic Vehicle class and includes officer badge information.
 */
class PatrolCar extends Vehicle {

    private LinkedList<String> officerBadges; // List of officer badge numbers

    /**
     * Constructs a PatrolCar with the given bumper number, type, status, and
     * officer badge list.
     *
     * @param bumperNum     Unique bumper number identifying the patrol car.
     * @param type          The type/category of the vehicle, should be "Police".
     * @param status        Current operational status.
     * @param officerBadges List of badge numbers of assigned officers.
     */
    public PatrolCar(String bumperNum, String type, String status, LinkedList<String> officerBadges) {
        super(bumperNum, type, status);
        this.officerBadges = officerBadges;
    }

    /**
     * Returns the list of officer badge numbers.
     *
     * @return A LinkedList of officer badge strings.
     */
    public LinkedList<String> getOfficers() {
        return officerBadges;
    }

    /**
     * Displays patrol car-specific information including officer badges.
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Officer Badges: " + officerBadges);
    }
}