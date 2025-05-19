/**
 * @Author Ian Cunningham
 * @Date 5/19/2025
 * @File PatrolCar.java
 * @Version 0.01
 * 
 * This is the child class PatrolCar from Vehicle
 */
import java.util.LinkedList;
 public class PatrolCar extends Vehicle {

    //list of officers
    LinkedList<String> officers = new LinkedList<>();

    /**
     * Constructor for a PatrolCar that has one officer
     * @Param String: bumper, type, driver
     */
public PatrolCar(String bumperNum, String type, String badgeNum) {
     super(bumperNum, type, badgeNum);
     officers.add(badgeNum);
    
}
/**
 * Constructor for a PatrolCar that has two officers. The first badge number is added
 * to the vehicle class and both are added to the officers linkedlist
 * @Param String: bumper, type driver
 */
public PatrolCar(String bumperNum, String type, String badgeNum, String badgeNum2) {
    super(bumperNum, type, badgeNum);
    officers.add(badgeNum);
    officers.add(badgeNum2);
}

/**
 * Get method for the bumper
 * @return bumper
 */
public String getBumper() {
    return super.getBumper();
}

/**
 * Get method for the type
 * @return type
 */
public String getType() {
    return super.getType();
}

/**
 * Get method for the officers in the linkedlist 
 * @return string of officers
 */
public String getOfficers() {
    String sOfficers = "";
    for(int i=0;i< officers.size();i++) {
        sOfficers = sOfficers + officers.get(i);
        if(i!=officers.size()-1) sOfficers = sOfficers+", ";
    }
    return sOfficers;
}
 }