import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * @Author Ian Cunningham
 * @Date 5/27/2025
 * @File CallLog.java
 */
public class CallLog {
    
    //declaring variables
    private int numCalls;
    private String date;
    PriorityQueue <Call> callQueue = new PriorityQueue<>();

    /**
     * Constructor for CallLog that takes in the date
     * @param date
     */
    public CallLog(String date) {
        this.date = date;
    }

    /**
     * Adds a call to the priorityQueue
     * @param call
     */
    public void addCall(Call call) {
        callQueue.add(call);
        numCalls++;
    }

    /**
     * Removes a call from the callQueue
     * @param call
     */
    public void removeCall(Call call, String time) {
        call.closeCall(time);
        Iterator value = callQueue.iterator();
        for(int i=0;i<callQueue.size();i++) {
            if(value.next().equals(call)) {
                value.remove();
                History.addTriage(call);
            }
        }
    }

    /**
     * This is a method that prints all of the calls that are in the priority queue
     */
    public void printTriage() {
        Iterator value = callQueue.iterator();
        while(value.hasNext()) {
            System.out.println(value.next());
        }
    }

    /**
     * A getter method for the date
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * Ends the current date
     */
    public void endDate() {
        date = null;
    }

    /**
     * A getter method for the numCalls
     * @return numCalls
     */
    public int getNumCalls() {
        return numCalls;
    }


}
