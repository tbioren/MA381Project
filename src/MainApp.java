import java.io.IOException;

class MainApp {
    public static void main(String[] args) throws IOException {
        Generation g = new Generation(100, 1024, 0.001, false, 10, SelectionMethod.TOP_HALF);
        String prevOut = "";
        for(int i=0; i < 1000; i++) {
            for(int j=0; j < prevOut.length(); j++) {
                System.out.print("\b");
            }
            prevOut = "Generation " + i + ": Average Fitness: " + g.getAverageFitness();
            System.out.print(prevOut);
            g.evolve();
        }
        System.out.println();
        System.out.println("Best Fitness: " + g.getBestFitness());
    }
}