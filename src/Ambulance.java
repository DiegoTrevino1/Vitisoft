/**
 * @Author Ian Cunningham
 * @Date 5/19/2025
 * @File Ambulance.java
 * @Version 0.01
 * 
 * This is the ambulance class which is a child form the vehicle class. It contains two linked lists, one
 * for the equipment and one for the EMT's/Medics badge number. It must take two badgeNumbers as ambulances
 * always have 2 people in them and an option to add a third rider
 */
import java.util.LinkedList;
public class Ambulance extends Vehicle{
    
    //list of riders in ambulance
    LinkedList<String> riders = new LinkedList<>();

    //list of equipment in ambulance
    LinkedList<String> equipment = new LinkedList<>();

    //if there is a medic on board or not
    private boolean medic;


    /**
     * Constructor for an Ambulance which must take two riders and if there is a medic or not
     * @param bumperNum
     * @param type
     * @param badgeNum
     * @param badgeNum2
     * @param medic
     */

    public Ambulance(String bumperNum, String type, String badgeNum, String badgeNum2, boolean medic) {
        super(bumperNum, type, badgeNum);
        riders.add(badgeNum);
        riders.add(badgeNum2);
        this.medic = medic;
        setEquipment();
    }

    /**
     * Populates the equipment linkedlist
     */
    public void setEquipment() {
        equipment.add("Lifepack");
        equipment.add("Lucas device");
        equipment.add("Crashbag");
        equipment.add("O2 bag");
    }

    /**
     * Adds a particular item to the equipment linkedlist
     * @param item
     */
    public void addEquipment(String item) {
        equipment.add(item);
    }

    /**
     * removes a specific string from the equpiment and returns if it was successful or not
     * @param item
     * @return boolean value
     */
    public boolean removeEquipment(String item) {
        boolean a = equipment.remove(item);
        return a;
    }

    /**
     * Adds a rider to the rider linkedlist
     * @param badgeNum
     */
    public void addRider(String badgeNum) {
        riders.add(badgeNum);
    }

    /**
     * removes a specific string from the riders and returns if it was successful or not
     * @param item
     * @return boolean value
     */
    public boolean removeRider(String badgeNum) {
        boolean a = riders.remove(badgeNum);
        return a;
    }

    /**
     * Returns if there is a medic on board
     * @return boolean medic
     */
    public boolean isMedic() {
        return medic;
    }

    /**
     * Returns the license plate number
     * @return Stirng getBumper
     */
    public String getBumper() {
        return super.getBumper();
    }

    /**
     * Returns the type of vehicle
     * @return
     */
    public String getType() {
        return super.getType();
    }

    /**
     * Appends all of the strings from the linkedlist riders and returns
     * @return riders
     */
    public String getRiders() {
        String sRiders = "";
        for(int i=0;i< riders.size();i++) {
            sRiders = sRiders + riders.get(i);
            if(i!=riders.size()-1) sRiders = sRiders+", ";
    }
    return sRiders;
    }

    /**
     * Appends all of the equipment from the linkedlist and returns
     * @return Equipment
     */
    public String getEquipment() {
        String sEquipment = "";
        for(int i=0;i< equipment.size();i++) {
            sEquipment = sEquipment + equipment.get(i);
            if(i!=equipment.size()-1) sEquipment = sEquipment+", ";
    }
    return sEquipment;
    }

}