public class DataCollection {
    private static final int NUM_TRIALS = 10, POPULATION_SIZE = 100, CHROMOSOME_SIZE = 1024, ELITISM_COUNT = 10;
    private static final double MUTATION_RATE = 0.001;
    private static final boolean USE_CROSSOVER = false;
    private static final SelectionMethod[] SELECTION_METHOD = { SelectionMethod.TOP_HALF, SelectionMethod.BEST_RANDOM_WORST };

    public static void main (String[] args) {
        for (SelectionMethod sm : SELECTION_METHOD) {
            double totalFitness = 0;
            System.out.print("Using selection method " + sm.toString().toLowerCase() + " Trial  ");
            for (int i = 0; i < NUM_TRIALS; i++) {
                for (int j = 0; j <= i / 10; j++) System.out.print("\b");
                System.out.print(i + 1);
                Generation g = new Generation(POPULATION_SIZE, CHROMOSOME_SIZE, MUTATION_RATE, USE_CROSSOVER, ELITISM_COUNT, sm);
                for (int j = 0; j < 1000; j++) g.evolve();
                totalFitness += g.getBestFitness();
            }
            System.out.println("\nSelection Method: " + sm.toString().toLowerCase() + "\tAverage Best Fitness: " + totalFitness / NUM_TRIALS);
        }
    }
}
