package Utils;

import java.util.List;
import java.util.Random;

public class Utils {

	public static float max(List<Float> busyTimes) {
		float max = 0;
		
		for(int i=0; i<busyTimes.size(); i++) {
			if(busyTimes.get(i) > max) {
				max = busyTimes.get(i);
			}
		}
		
		return max;
	}
	
	public static float sum(List<Float> busyTimes) {
		float sum = 0;
		
		for(int i=0; i<busyTimes.size(); i++) {
			sum += busyTimes.get(i);
		}
		
		return sum;
	}
	
	public static int randInt(int min, int max) {
		Random rand = new Random();

	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
}
