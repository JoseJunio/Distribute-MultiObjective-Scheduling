package Task;

public class Task implements Comparable<Task>{

    public int tid;
    
    /*The completion time*/
    float cTime;

    /*The time required to execute the task on the machine on which it has been scheduled*/
    float eTime;

    public Task(int task_id){
        tid=task_id;
    }

    public float get_cTime() {
        return cTime;
    }

    public void set_cTime(float cTime) {
        this.cTime = cTime;
    }

    public float get_eTime() {
        return eTime;
    }

    public void set_eTime(float eTime) {
        this.eTime = eTime;
    }

	@Override
	public int compareTo(Task o) {
		// TODO Auto-generated method stub
		return 0;
	}
    
}
