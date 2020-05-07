package SimulateAG;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import Utils.Utils;

public class Population {

	public int size;
	//public List<Individual> individuals;
	//public Queue<Individual> individuals;
	public LinkedList<Individual> individuals;
	public int generation;
	public Individual bestIndividual;
	
	
	public Population(int size) {
		this.size = size;
		this.individuals = new LinkedList<Individual>();
		this.generation = 0;
	}
	
	public static Population generate(int size, int machines, int tasks) {
		Population newPopulation = new Population(size);
		
		while(newPopulation.getIndividuals().size() < size) {
			float makespan = -1;
			
			if(newPopulation.getIndividuals().size() >= 1) {
				makespan = newPopulation.getBestIndividual().getMakespan();
			}
			
			Individual newIndividual = Individual.generate(newPopulation.getGeneration(), tasks, machines, makespan);
			
			if(!newPopulation.is_duplicate(newIndividual)) {
				newPopulation.getIndividuals().push(newIndividual);
			}
			
			newPopulation.setBestIndividual(newPopulation.getIndividuals().get(0));
			
		}
		return newPopulation;
	}
	
	public boolean is_duplicate(Individual individualToInsert) {
		for(int i=0; i< getIndividuals().size(); i++) {
			
			Individual individual = getIndividuals().get(i);
			if(individualToInsert.getGenotype() == individual.getGenotype()) {
				return true;
			}
		}
		return false;
	}
	
	public List<Tuple> select_parents(int crossoverFactor) {
		List<Float> parents = new ArrayList<>();

		int numberOfParents = (int) (this.size  * (crossoverFactor / 100));
		
		if(numberOfParents % 2 == 0) {
			numberOfParents -= 1;
		}
		
		int numberOfPairs = (int) (numberOfParents / 2);
		
		List<Tuple> tuples = new ArrayList<Tuple>();
		//List<Individual> tuples = new ArrayList();
		
		for(int i=0; i < numberOfPairs; i++) {
			Tuple tuple = select_pair();
			/*tuples.add(tuple.getParent1());
			tuples.add(tuple.getParent2());*/
			tuples.add(select_pair());
		}
		
		return tuples;
	}
	
	public Tuple select_pair() {
		Tuple tuple;
		
		int indexParent1 = Utils.randInt(0, this.getIndividuals().size() - 1);
		int indexParent2 = 0;
		
		while(true) {
			indexParent2 = Utils.randInt(0, this.getIndividuals().size() - 1);
			
			if(indexParent2 != indexParent1) {
				break;
			}
		}
		
		tuple = new Tuple(this.getIndividuals().get(indexParent1), this.getIndividuals().get(indexParent2));
		
		return tuple;
	}

	public float makespan_sum() {
		float total_sum = 0;
		
		for(int i=0; i<this.getIndividuals().size(); i++) {
			total_sum += this.getIndividuals().get(i).getMakespan();
		}
		
		return total_sum;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public LinkedList<Individual> getIndividuals() {
		return (LinkedList<Individual>) individuals;
	}

	public int getGeneration() {
		return generation;
	}

	public void setGeneration(int generation) {
		this.generation = generation;
	}

	public Individual getBestIndividual() {
		return bestIndividual;
	}

	public void setBestIndividual(Individual bestIndividual) {
		this.bestIndividual = bestIndividual;
	}
	
	public void insert_individual(Individual newIndividual) {
		getIndividuals().push(newIndividual);
		Individual best = getIndividuals().get(0);
		setBestIndividual(best);
	}
	
	public void update_population(int elitismFactor) {
		LinkedList<Individual> newPopulation = new LinkedList<Individual>();
		
		int nElitism = (int) (this.size * (elitismFactor / 100));
		
		for(int i=0; i < nElitism; i++) {
			newPopulation.push(getIndividuals().pop());
		}
		
		for(int i=0; i < (this.size - nElitism); i++) {
			Individual selectedIndividual = select_individual();
			newPopulation.push(selectedIndividual);
		}
		
		this.individuals = newPopulation;
		
	}
	
	public Individual select_individual() {
		int index = Utils.randInt(0, this.individuals.size() - 1);
		return this.individuals.get(index);
	}
	
	/*public class Tuple {
		Individual parent1;
		Individual parent2;
		
		public Tuple(Individual parent1, Individual parent2) {
			this.parent1 = parent1;
			this.parent2 = parent2;
		}

		public Individual getParent1() {
			return parent1;
		}

		public Individual getParent2() {
			return parent2;
		}
	}*/
	
}
