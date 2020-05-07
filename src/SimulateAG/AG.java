package SimulateAG;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import Parameters.Parameters;
import Task.Task;

public class AG {

	private int machines;
	private int tasks;
	private String fileName;
	private int populationSize;
    private int mutationFactor;
    private int crossoverFactor; 
    private int elitismFactor;
    private int crossoverOperator; 
    private int mutationOperator; 
    private int numExecution;
    
    public Individual individual;
	
	public AG(Parameters parameters) {
	    this.fileName = parameters.getFileName();
	    this.machines = parameters.get_NUM_MACHINES(); 
        this.tasks = parameters.get_NUM_TASKS(); 
        this.populationSize = parameters.getPopulationMaskespan(); 
        this.mutationFactor = parameters.getPercMutation();
        this.crossoverFactor = parameters.getPercCrossover(); 
        this.elitismFactor = parameters.getPercElitism(); 
        this.crossoverOperator = parameters.getOperCrossover(); 
        this.mutationOperator = parameters.getOperMutation(); 
        this.numExecution = parameters.getNumExecution();
	}
	 
	public void execute(){
		Individual individual = new Individual(this.tasks, this.machines, loadTasks());
		
		//Population makespanPopulation = Population.generate(this.populationSize, this.machines, this.tasks);
		Population makespanPopulation = Population.generate(this.populationSize, this.machines, individual.getTasks().size());
		
		for(int i=0; i<this.numExecution; i++){
			float currentMaskepan = makespanPopulation.makespan_sum();
			
			// SELECAO
			List<Tuple> parents = makespanPopulation.select_parents(this.crossoverFactor);
			//List<Individual> parents = makespanPopulation.select_parents(this.crossoverFactor);
			
			//CROSSOVER
			//List<Tuple> childs = new ArrayList();
			List<Individual> childs = new ArrayList();
			if(this.crossoverOperator == 1) {
				childs = Individual.crossover_one_point(parents, i+1, currentMaskepan, this.tasks);
				System.out.println("childs: "   + childs);
			}
			
			if(this.mutationOperator == 1) {
				for(int j=0; j < childs.size(); j++) {
					Individual child = childs.get(j);
					child.apply_mutation_simple(this.mutationOperator, currentMaskepan);
					makespanPopulation.insert_individual(child);
				}
			}
			
			makespanPopulation.update_population(this.elitismFactor);
			
		}
		
	}
	
	public List<Task> loadTasks() {
		
		List<Task> tasks = new ArrayList<Task>();
		
		try {
			
			File file = new File(fileName);
			FileReader fileReader = new FileReader(file);
		
			BufferedReader br = new BufferedReader(fileReader); 
			
			List<Float> tasksTimes = new ArrayList<Float>();
			
			String st;
			
			while ((st = br.readLine()) != null) {
				tasksTimes.add(Float.parseFloat(st));
			}
			
			for(int i=0; i < this.tasks; i++) {
				  Task task = new Task(i);
				for(int j=0; j< this.machines; j++) {
					//System.out.println(tasksTimes.get(i * this.machines + j));
					task.setMachineTimes(tasksTimes.get(i * this.machines + j));
				}
				tasks.add(task);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return tasks;

	}
	
}
