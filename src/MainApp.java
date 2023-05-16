import java.io.IOException;

class MainApp {
    public static void main(String[] args) throws IOException {
        Generation g = new Generation(100, 100, 0.01, false, 10, SelectionMethod.TOP_HALF);
        for(int i=0; i < 100; i++) {
            g.printAverageFitness();
            g.evolve();
        }
        g.sortPopulation();
        System.out.println();
    }
}