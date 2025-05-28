import java.util.LinkedList;

/**
 *@Author Ian Cunningham
 *@Date 5/27/2025
 *@File Call.java
 * This is the call file to make calls
 */  
public class Call {

    //declaring variables
    private int callNum;
    private String callType;
    private int priority;
    private String creator;
    private String startTime;
    private String endTime;
    private String callDescription;
    private String callLocation;
    private LinkedList<Call> callLog;
    private LinkedList<Vehicle> assignedVehicles;

    /**
     * This is the constructor for a Call
     * @param time
     * @param type
     * @param description
     * @param location
     * @param username
     */
    public Call(String time, String type, String description, String location, String username, int priority, int callNum) {
        this.startTime = time;
        this.callType = type;
        this.callDescription = description;
        this.callLocation = location;
        this.creator = username;
        this.priority = priority;
        this.callNum = callNum;

    }

    /**
     * This is to add callnotes to a call
     * @param item
     * @param username
     * @param time
     */
    public void addLog(String item, String username, String time) {
        callDescription = callDescription + "\n["+time+"] "+username+": "+item;
    }

    /**
     * This is to print all of the info about a call
     */
    public void printLog() {
        System.out.println(callDescription);
    }

    /**
     * This is to add a vehicle to an active call, it takes the vehicle object and the time and adds
     * this to the callDescription for documentation purposes
     * @param vehicle
     * @param time
     */
    public void addVehicle(Vehicle vehicle, String time) {
        assignedVehicles.add(vehicle);
        callDescription = callDescription + "\n["+time+"] Dispatched "+vehicle.getType()+": "+vehicle.getBumper();
    }

    /**
     * This is to "clear" a vehicle from a call. It takes in the bumperNumber and the time and
     * adds this to the callDescription for documentation purposes
     * @param bumperNumber
     * @param time
     */
    public void removeVehicle(String bumperNumber, String time) {
        for(int i=0;i<assignedVehicles.size();i++) {
            if(assignedVehicles.get(i).getBumper().equals(bumperNumber)) {
                assignedVehicles.remove(i);
                callDescription = callDescription + "\n["+time+"] Cleared: " + bumperNumber;
            }
        }
    }

    public String getLocation() {
        return callLocation;
    }

    public String getDescription() {
        return callDescription;
    }

    public String getStart() {
        return startTime;
    }

    public String getCreateor() {
        return creator;
    }

    public void closeCall(String time) {
        callDescription = callDescription +"\n ["+time+"]: Call closed";
        this.endTime = time;
    }

    public String getEnd() {
        return endTime;
    }

    public int getPriority() {
        return priority;
    }

    public String getType() {
        return callType;
    }

    public int getCallNum() {
        return callNum;
    }

    public String getTime() {
        return startTime;
    }

    /**
     * This is a comparator to compare the priority of each call
     * @param call
     * @return compare value between two calls
     */
    public int comparteTo(Call call) {
        return Integer.compare(call.priority, this.priority);
    }

}
