/**
 * @Author Ian Cunningham
 * @Date 5/19/2025
 * @File FireEngine.java
 * @Version 0.01
 * 
 * This is the child of Vehicle for Fire Engines
 */
import java.util.LinkedList;
public class FireEngine extends Vehicle{
    
    //linked list for the equipment
    LinkedList<String> equipment = new LinkedList<>();


    /** 
     * Constructor for FireEngine
     * @param bumperNum
     * @param type
     * @param driver
     */
    public FireEngine(String bumperNum, String type, String driver) {
        super(bumperNum, type, driver);
        setEquipment();
    }

    /** 
     * Populates the equipment linkedlist
     */
    public void setEquipment() {
        equipment.add("Hose");
        equipment.add("Jaws of Life");
        equipment.add("Axe");
        equipment.add("Cutters");
    }

    /**
     * @return bumperNum
     */
    public String getBumper() {
        return super.getBumper();
    }

    /**
     * @return type
     */
    public String getType() {
        return super.getBumper();
    }

    /**
     * Appends all of the equipment from the linked list to a string and returns
     * @return String: Equipment
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


