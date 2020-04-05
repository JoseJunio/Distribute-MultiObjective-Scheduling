package SimulateEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parameters {

	private int 				NUM_MACHINES;
    private int 				NUM_TASKS;
    private String				fileName;

    public Parameters() {
    	
    	String pathFile = System.getProperty("user.dir") + "/parameters/parameters.txt";
    	
    	File file = new File(pathFile);
		FileReader fileReader;
		
		try {
		
			fileReader = new FileReader(file);
		
			Pattern p = Pattern.compile("(?:.*?= )(.*?)(?=\\n\\d:|$)");
			
			BufferedReader br = new BufferedReader(fileReader); 
			
			String line;
			int count = 0;
			
			while ((line = br.readLine()) != null) {
				
				Matcher m = p.matcher(line);
				
				if(m.matches()) {
					switch(count) {
				 		case 0: 
				 			NUM_MACHINES = Integer.parseInt(m.group(1));
				 			break;
				 		case 1: 
				 			NUM_TASKS = Integer.parseInt(m.group(1));
				 			break;
				 		case 2: 
				 			fileName = m.group(1);
				 			break;
					}
					count++;
				}
			}
			
		} catch (Exception  e) {
			e.printStackTrace();
		}
    
    }

	public int get_NUM_MACHINES() {
		return NUM_MACHINES;
	}

	public void set_NUM_MACHINES(int nUM_MACHINES) {
		NUM_MACHINES = nUM_MACHINES;
	}

	public int get_NUM_TASKS() {
		return NUM_TASKS;
	}

	public void set_NUM_TASKS(int nUM_TASKS) {
		NUM_TASKS = nUM_TASKS;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
    
    
    
    
}
