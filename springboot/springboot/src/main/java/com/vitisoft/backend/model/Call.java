import java.time.LocalDateTime;
import java.util.LinkedList;

/**
 *@Author Ian Cunningham
 *@Date 5/27/2025
 *@File Call.java version 0.2
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
    private String date;
    private LinkedList<Vehicle> assignedVehicles;
    DatabaseManager db;

    /**
     * This is the constructor for a Call
     * @param time
     * @param type
     * @param description
     * @param location
     * @param username
     */
    public Call(String time, String type, String description, String location, String username, int priority, int callNum, String date) {
        db.connect();
        this.startTime = time;
        this.callType = type;
        this.callDescription = description;
        this.callLocation = location;
        this.creator = username;
        this.priority = priority;
        this.callNum = callNum;
        this.date = date;
        Emergency emergency = new Emergency(username, null, location, username, date, type, true, priority);
        db.insertEmergency(emergency);

    }

    /**
     * This is to add callnotes to a call, taking in the 'item' as the notes.
     * @param item
     * @param username
     * @param time
     */
    public void addLog(String item, String username) {
        LocalDateTime now = LocalDateTime.now();
        callDescription = callDescription + "\n["+now+"] "+username+": "+item;
        EmergencyUpdate update = new EmergencyUpdate(callNum, now, callDescription);
        db.insertEmergencyUpdate(update);
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
    public void addVehicle(Vehicle vehicle) {
        LocalDateTime now = LocalDateTime.now();
        assignedVehicles.add(vehicle);
        callDescription = callDescription + "\n["+now+"] Dispatched "+vehicle.getType()+": "+vehicle.getBumper();
        EmergencyUpdate update = new EmergencyUpdate(callNum, now, callDescription);
        db.insertEmergencyUpdate(update);

    }

    /**
     * This is to "clear" a vehicle from a call. It takes in the bumperNumber and the time and
     * adds this to the callDescription for documentation purposes
     * @param bumperNumber
     * @param time
     */
    public void removeVehicle(String bumperNumber) {
        LocalDateTime now = LocalDateTime.now();
        for(int i=0;i<assignedVehicles.size();i++) {
            if(assignedVehicles.get(i).getBumper().equals(bumperNumber)) {
                assignedVehicles.remove(i);
                callDescription = callDescription + "\n["+now+"] Cleared: " + bumperNumber;
                EmergencyUpdate update = new EmergencyUpdate(callNum, now, callDescription);
                db.insertEmergencyUpdate(update);
            }
        }
    }

    /**
     * Getter method for the callLocation
     * @return callLocation
     */
    public String getLocation() {
        return callLocation;
    }

    /**
     * Getter method for the call description
     * @return callDescription
     */
    public String getDescription() {
        return callDescription;
    }

    /**
     * Getter method for the start time of the call
     * @return startTime
     */
    public String getStart() {
        return startTime;
    }

    /**
     * Getter method for the creator of the call
     * @return creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method closes the call and sets the endTime to the current time while taking in the current time
     * @param time
     */
    public void closeCall(String time) {
        callDescription = callDescription +"\n ["+time+"]: Call closed";
        this.endTime = time;
    }

    /**
     * This is a getter method for the endTime
     * @return endTime
     */
    public String getEnd() {
        return endTime;
    }

    /**
     * This is a getter method for the priority on a call
     * @return priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * This is a getter method for the callType
     * @return callType
     */
    public String getType() {
        return callType;
    }

    /**
     * This is a getter method for the callNum
     * @return callNum
     */
    public int getCallNum() {
        return callNum;
    }

    /**
     * This is a getter method for the startTime
     * @return startTime
     */
    public String getTime() {
        return startTime;
    }

    /**
     * A getter method for the date
     * @return date
     */
    public String getDate() {
        return date;
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
