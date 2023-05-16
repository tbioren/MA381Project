import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Generation {
    Chromosome[] population;
    double mutationRate;
    boolean crossover;
    int elitismCount;
    int chromosomeSize;
    SelectionMethod selectionMethod;

    public Generation(int populationSize, int chromosomeSize, double mutationRate, boolean crossover, int elitismCount, SelectionMethod selectionMethod) {
        this.selectionMethod = selectionMethod;
        this.mutationRate = mutationRate;
        this.crossover = crossover;
        this.elitismCount = elitismCount;
        this.chromosomeSize = chromosomeSize;
        population = new Chromosome[populationSize];
        for(int i = 0; i < populationSize; i++) {
            population[i] = new Chromosome(chromosomeSize);
        }
    }

    public Chromosome[] getPopulation() {
        return population;
    }

    public void updateFitness() {
        for(Chromosome chromosome : population) {
            chromosome.updateFitness();
        }
    }

    public void sortPopulation() {
        updateFitness();
        Arrays.sort(population);
    }

     /**
     * Creates a new generation of chromosomes with random genes
     */
    public void evolve() {
        sortPopulation();
        // Separate the elite chromosomes from the rest of the generation
        ArrayList<Chromosome> eliteChromosomes = getElites(elitismCount);
        ArrayList<Chromosome> genSansElites = trimElites(eliteChromosomes);
        ArrayList<Chromosome> bestChromosomes;
        updateFitness();
        // Select the best chromosomes from the generation with the given selection method
        switch(selectionMethod) {
            default:
                bestChromosomes = selectTopHalf(genSansElites);
                break;
            case ROULETTE:
                bestChromosomes = selectRoulette(genSansElites);
                break;
            case RANK:
                bestChromosomes = selectRank(genSansElites);
                break;
            case BEST_RANDOM_WORST:
                bestChromosomes = selectBestRandomWorst(genSansElites);
                break;
        }
        if(crossover) bestChromosomes = crossover(bestChromosomes); // Crossover the best chromosomes LEAVE COMMENTED FOR M2
        ArrayList<Chromosome> newGeneration = mutate(bestChromosomes, mutationRate);

        // Since you cant have half a chromosome, if the elitism number is odd, remove the first chromosome (the worst one)
        if (elitismCount % 2 != 0){
            newGeneration.remove(0);
        }

        // Add the elite chromosomes back into the generation
        for(Chromosome chromosome : eliteChromosomes) {
            newGeneration.add(new Chromosome(chromosome.getGenes()));
        }
        population = newGeneration.toArray(new Chromosome[0]);
    }

    public void printAverageFitness() {
        System.out.println("Average fitness: " + getAverageFitness());
    }

    public double getAverageFitness() {
        double totalFitness = 0;
        for(Chromosome chromosome : population) {
            totalFitness += chromosome.updateFitness();
        }
        return (double)totalFitness/population.length;
    }

    // Creates 2 mutant children from each parent
    private ArrayList<Chromosome> mutate(ArrayList<Chromosome> bestChromosomes, double mutationRate) {
        ArrayList<Chromosome> newGeneration = new ArrayList<Chromosome>();
        for(int i = 0; i < bestChromosomes.size(); i++) {
            newGeneration.add(new Chromosome(bestChromosomes.get(i).getGenes()));
            newGeneration.add(new Chromosome(bestChromosomes.get(i).getGenes()));
        }
        for(Chromosome chromosome : newGeneration) {
            chromosome.mutate(mutationRate);
        }
        return newGeneration;
    }

    // Crossover. It's complicated so look at the technical documentation if you're big enough of a nerd to care
    private ArrayList<Chromosome> crossover(ArrayList<Chromosome> bestChromosomes) {
        System.out.println("Crossover");
        ArrayList<Chromosome> newGeneration = new ArrayList<Chromosome>();
        int crossoverPoint = bestChromosomes.get(0).getGenes().length/2;
        for(int i=0; i < bestChromosomes.size(); i+=2) {
            char[] parent1Start = Arrays.copyOfRange(bestChromosomes.get(i).getGenes(), 0, crossoverPoint);
            char[] parent1End = Arrays.copyOfRange(bestChromosomes.get(i).getGenes(), crossoverPoint, chromosomeSize);
            char[] parent2Start = Arrays.copyOfRange(bestChromosomes.get(i+1).getGenes(), 0, crossoverPoint);
            char[] parent2End = Arrays.copyOfRange(bestChromosomes.get(i+1).getGenes(), crossoverPoint, chromosomeSize);
            char[] child1 = new char[chromosomeSize];
            char[] child2 = new char[chromosomeSize];
            for(int j=0; j < parent1Start.length; j++) {
                child1[j] = parent1Start[j];
                child2[j] = parent2Start[j];
            }
            for(int j=0; j < parent1End.length; j++) {
                child1[j+crossoverPoint] = parent1End[j];
                child2[j+crossoverPoint] = parent2End[j];
            }
            newGeneration.add(new Chromosome(child1));
            newGeneration.add(new Chromosome(child2));
        }
        return newGeneration;
    }

    // Selects the best chromosomes from the generation to pass straight through to the next generation
    private ArrayList<Chromosome> getElites(double elitismNumber) {
        ArrayList<Chromosome> elites = new ArrayList<Chromosome>();
        for(int i=0; i < elitismNumber; i++) {
            elites.add(population[population.length-i-1]);
        }
        return elites;
    }

    private ArrayList<Chromosome> trimElites(ArrayList<Chromosome> elites) {
        ArrayList<Chromosome> newGen = new ArrayList<Chromosome>();

        for (int i = 0; i < population.length - elites.size(); i++) {
            newGen.add(new Chromosome(population[i].getGenes()));
        }

        return newGen;
    }

    // Selects the top 1/2 of the generation to pass through to the next generation
    private ArrayList<Chromosome> selectTopHalf(ArrayList<Chromosome> newGen) {
        ArrayList<Chromosome> newGeneration = new ArrayList<Chromosome>();
        for(int i=newGen.size()/2; i < newGen.size(); i++) {
            char[] genes = Arrays.copyOf(newGen.get(i).getGenes(), newGen.get(i).getGenes().length);
            newGeneration.add(new Chromosome(genes));
        }
        return newGeneration;
    }

    // Selects the chromosomes from the generation with the given selection method (I'm not sure if this works)
    private ArrayList<Chromosome> selectRoulette(ArrayList<Chromosome> newGen) {
        for(Chromosome chromo : newGen) {
            chromo.updateFitness();
        }
        ArrayList<Integer> roulette = new ArrayList<>();
        for (int i = 0; i < newGen.size(); i++) {
            for (int j = 0; j < newGen.get(i).getFitness(); j++) {
                roulette.add(i);
            }
        }
        ArrayList<Chromosome> newGeneration = new ArrayList<Chromosome>();
        for(int i=0; i < (newGen.size()+1)/2; i++) {
            int index = roulette.get((int) (Math.random() * roulette.size()));
            char[] genes = Arrays.copyOf(newGen.get(index).getGenes(), newGen.get(index).getGenes().length);
            newGeneration.add(new Chromosome(genes));
        }
        return newGeneration;
    }

    // Selects the chromosomes from the generation with the given selection method (I'm not sure if this works)
    private ArrayList<Chromosome> selectRank(ArrayList<Chromosome> newGen) {
        for(int i=0; i < newGen.size(); i++) {
            newGen.get(i).setFitness(i+1);
        }
        return selectRoulette(newGen);
    }

    private ArrayList<Chromosome> selectBestRandomWorst(ArrayList<Chromosome> newGen) {
        ArrayList<Chromosome> nextGen = new ArrayList<Chromosome>();
        updateFitness();
        for(int i=0; i < newGen.size()/6; i++) {
            char[] genes = Arrays.copyOf(newGen.get(i).getGenes(), newGen.get(i).getGenes().length);
            nextGen.add(new Chromosome(genes));
            genes = Arrays.copyOf(newGen.get(newGen.size() - i-1).getGenes(), newGen.get(newGen.size() - i-1).getGenes().length);
            nextGen.add(new Chromosome(genes));
        }
        while(nextGen.size() < newGen.size()/2) {
            nextGen.add(newGen.get((int) (Math.random() * newGen.size())));
        }
        return nextGen;
    }

    public String toString() {
        return getAverageFitness() + "";
    }
}
