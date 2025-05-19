/**
 * @Author Ian Cunningham
 * @Date 5/19/2025
 * @File Vehicle.java
 * @Version 0.01
 * 
 * This is the parent vehicle class for storing vehicle data
 */

public class Vehicle {

    //Declare variables
    private String bumperNum;
    private String type;
    private String driver;

    /**
     * This is the constructor for the vehicle with a single driver
     * @param String Bumper num, String type, String driver
     */
    public Vehicle(String bumperNum, String type, String driver) {
        this.bumperNum = bumperNum;
        this.type = type;
        this.driver = driver;
    }

    /**
     * Returns the bumperNum value
     * @return bumperNum
     */
    public String getBumper() {
        return bumperNum;
    }

    /**
     * Returns the type value
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the driver
     * @return driver
     */
    public String getDriver() {
        return driver;
    }


}
