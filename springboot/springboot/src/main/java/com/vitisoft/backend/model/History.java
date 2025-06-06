/**
 * @Author Ian Cunningham
 * @Date 5/27/2025
 * @File History.java
 * This is a file to story the history of calls
 * 
 * CURRENTLY THIS IS NOT CONNECTED WITH THE DATABASE
 */

import java.util.*;
public class History {
    
    //declaring variables
    private static int numCalls;
    static HashMap<Integer, Call> history;

    /**
     * This is the constructor for the history
     */
    public History() {
            numCalls++;
    }
    /**
     * This adds a triage to the hashmap
     * @param triage
     */
    public static void addTriage(Call call) {
        history.put(call.getCallNum(), call);
        numCalls++;
    }

    /**
     * This prints all of the calls that are in the hashmap
     */
    public void printHistory() {
        for(int i=0; i<history.size();i++) {
            System.out.println(history.get(i)+"\n");
        }
    }

    /**
     * This prints a call from a particular time
     * @param date
     * @return Triage
     */
    public Call findDay(String date, String time) {
        for(int i=0;i<history.size();i++) {
            if(history.get(i).getTime().equals(time) && history.get(i).getDate().equals(date)) {
                return history.get(i);
            }
        }
        return null;
    }

}
