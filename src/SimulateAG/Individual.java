package SimulateAG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;

import Task.Task;
import Utils.Utils;

public class Individual {

	private int nextIndividualIdentifier = 0;
	private int numTasks = 0;
	private int numMachines = 0;
	//private float[] tasks;
	private List<Task> tasks = new ArrayList<Task>();
	private List<Integer> crossoverMask = new ArrayList<Integer>();
	
	private int generation;
	private float makespan;
	private float flowtime;
	private float utilization;
	//private List<Integer> genotype;
	private HashMap<Integer, Integer> genotype;
	private int identifier;
	
	//private Individual(int numTasks, int numMachines, List<Task> tasks, int[] crossoverMask) {
	public Individual(int numTasks, int numMachines, List<Task> tasks) {
		this.numMachines = numMachines;
		this.numTasks = numTasks;
		this.tasks = tasks;
		generate_crossover_mask();
	}
	
	public Individual(int generation) {
		this.generation = generation;
		//this.genotype = new ArrayList<Integer>();
		this.genotype = new HashMap<Integer, Integer>();
		this.makespan = 0;
		this.flowtime = 0;
		this.utilization = 0;
		this.identifier = this.nextIndividualIdentifier;
		this.nextIndividualIdentifier += 1;
	}
	
	public void generate_crossover_mask() {
		Random random = new Random();
		
		for(int i=0; i < this.numTasks; i++) {
			int assigneMachine = random.nextInt(2) + 1;
			System.out.println(assigneMachine);  
			this.crossoverMask.add(assigneMachine);		
 		}
	}
	
	public static Individual generate(int generation, int tasks, int machines, float makespan) {
		Random random = new Random();
		
		Individual newIndividual = new Individual(generation);
		
		for(int t=0; t < tasks; t++) {
			int assigneMachine = random.nextInt((machines - 1) + 1) + 1; 
			newIndividual.setGenotype(t, assigneMachine);
		}
		
		newIndividual.calculate_fitness(makespan);
		
		return newIndividual;
	}

	public void calculate_fitness(float makespan) {
		List<Float> busyTimes = new ArrayList<Float>();
		
		for(int i=0; i < numMachines; i++) {
			busyTimes.add((float) 0.0);
		}
		
		for(int j=0; j < numTasks; j++) {
			Task task = tasks.get(j);
			int machine = genotype.get(j) - 1;
			busyTimes.add(busyTimes.get(machine) + task.getMachineTimes().get(machine));
		}
		
		this.makespan = Utils.max(busyTimes);
		this.flowtime = Utils.sum(busyTimes);
		this.calculate_utilization(makespan);
	}
	
	public void calculate_utilization(float populationMakespan) {
		this.utilization = this.makespan / (this.numMachines * populationMakespan);
	}
	
	public static List<Individual> crossover_one_point(List<Tuple> parents, int generation, float makespan, int numTasks) {
		//int numberOfPairs = (int) (parents.size() / 2);
		
		//List<Tuple> childs = new ArrayList();
		List<Individual> childs = new ArrayList();
		
		for(int i=0; i < parents.size(); i++) {
			Tuple tuple = parents.get(i);
			
			Individual parent1 = tuple.getParent1();
			Individual parent2 = tuple.getParent2();
			
			Individual child1 = parent1.make_copy(generation);
			Individual child2 = parent2.make_copy(generation);
			
			int crossoverPoint = Utils.randInt(1, numTasks - 1);
			
			for(int j=crossoverPoint; j < numTasks; j++) {
				int aux = child1.getGenotype().get(j);
				child1.setGenotype(j, child2.getGenotype().get(j));
				child2.setGenotype(j, aux);
			}
			
			child1.calculate_fitness(makespan);
			child2.calculate_fitness(makespan);
		
			/*Tuple newTuple = new Tuple(child1, child2);
			childs.add(newTuple);*/
			childs.add(child1);
			childs.add(child2);
		}
		
		return childs;
		
	}
	
	public void apply_mutation_simple(int mutationFactor, float currentMakespan) {
		int mutationRand = Utils.randInt(0, 100);
		
		if(mutationRand <= mutationFactor) {
			int indexMutation = Utils.randInt(0, this.getGenotype().size()-1);
			int newMachine = Utils.randInt(0, this.numMachines);
			setGenotype(indexMutation, newMachine);
		}
		
		calculate_fitness(currentMakespan);
	
	}
	
	public Individual make_copy(int generation) {
		Individual newIndividual = new Individual(generation);
		
		for(int i=0; i<this.numTasks; i++) {
			newIndividual.setGenotype(i, genotype.get(i));
		}
		
		return newIndividual;
	}
	
	/*public List<Integer> getGenotype() {
		return genotype;
	}

	public void setGenotype(int genotype) {
		this.genotype.add(genotype);
	}*/
	
	public float getMakespan() {
		return makespan;
	}

	public HashMap<Integer, Integer> getGenotype() {
		return genotype;
	}

	public void setGenotype(int key, int value) {
		this.genotype.put(key, value);
	}

	public void setMakespan(float makespan) {
		this.makespan = makespan;
	}

	public List<Task> getTasks() {
		return tasks;
	}
	
}
