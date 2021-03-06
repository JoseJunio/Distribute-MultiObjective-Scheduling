package Task;

import java.util.Comparator;

public class TaskComparator implements Comparator<Task>{

    public int compare(Task t1, Task t2) {
    	
        if(t1.get_cTime() < t2.get_cTime()) {
        	return -1;
        } else if(t1.get_cTime() == t2.get_cTime()) {
        	return 0;
        } else if(t1.get_cTime() > t2.get_cTime()) {
        	return 1;
        } else {
            return -2;
        }
    }
    
}
