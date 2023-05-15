import java.io.IOException;

class MainApp {
    public static void main(String[] args) throws IOException {
        Generation g = new Generation(100, 100, 0.001, false, 0, SelectionMethod.TOP_HALF);
        for(int i=0; i < 100; i++) {
            g.printAverageFitness();
            g.evolve();
        }
    }
}