package SimulateEngine;

import java.util.Iterator;
import java.util.Vector;

import Task.Task;

public class Heuristics {
	
	public static void schedule_MinMin(Vector<Task> metaSet, SimulateEngine sim){

        /*We do not actually delete the task from the meta-set rather mark it as removed*/
        boolean[] isRemoved=new boolean[metaSet.size()];

        /*Matrix to contain the completion time of each task in the meta-set on each machine.*/
        int c[][]=schedule_MinMinHelper(metaSet, sim);
        int i=0;

        int tasksRemoved=0;
        do{
            int minTime=Integer.MAX_VALUE;
            int machine=-1;
            int taskNo=-1;
            /*Find the task in the meta set with the earliest completion time and the machine that obtains it.*/
            for(i=0;i<metaSet.size();i++){
                if(isRemoved[i])continue;
                for(int j=0;j<sim.machines;j++){
                    if(c[i][j]<minTime){
                        minTime=c[i][j];
                        machine=j;
                        taskNo=i;
                    }
                }
            }           
            Task t = metaSet.elementAt(taskNo);
            sim.mapTask(t, machine);

            /*Mark this task as removed*/
            tasksRemoved++;
            isRemoved[taskNo]=true;
            //metaSet.remove(taskNo);

            /*Update c[][] Matrix for other tasks in the meta-set*/
            for(i=0;i<metaSet.size();i++){
            	System.out.println("Dentro:" + metaSet.get(i));
                if(isRemoved[i])continue;
                else{
                    c[i][machine]=(int) (sim.mat[machine]+ sim.etc[metaSet.get(i).tid][machine]);
                }
            }            
            
            System.out.println(tasksRemoved);
            
        }while(tasksRemoved!=metaSet.size());
        
        float teste = 0;
        
    }
	
	private static int[][] schedule_MinMinHelper(Vector<Task> metaSet, SimulateEngine sim){
        int c[][]=new int[metaSet.size()][sim.machines];
        int i=0;
        for(Iterator it=metaSet.iterator();it.hasNext();){
            Task t=(Task)it.next();
            for(int j=0;j<sim.machines;j++){
                c[i][j]=(int) (sim.mat[j]+sim.etc[t.tid][j]);
            }
            i++;
        }
        return c;
    }

}
