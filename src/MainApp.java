import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class MainApp {
    public static void main(String[] args) throws IOException {
        FileWriter fw;
        SelectionMethod[] methods = { SelectionMethod.TRUNCATION, SelectionMethod.ROULETTE, SelectionMethod.RANK,
                SelectionMethod.BEST_RANDOM_WORST };
        for (SelectionMethod method : methods) {
            for (int iteration = 0; iteration < 10; iteration++) {
                new File(String.format("output/%s-trial%d.csv", method, 1)).createNewFile();
                fw = new FileWriter(String.format("output/%s-trial%d.csv", method, iteration));
                Generation g = new Generation(100, 1200, 0.001, false, 10, method);
                String prevOut = "";
                for (int i = 0; i < 1000; i++) {
                    for (int j = 0; j < prevOut.length(); j++) {
                        System.out.print("\b");
                    }
                    prevOut = "Generation " + i + ": Average Fitness: " + g.getAverageFitness();
                    System.out.print(prevOut);
                    fw.append(i + "," + g.getBestFitness() + "\n");
                    g.evolve();
                }
                fw.close();
                System.out.println();
                System.out.println("Best Fitness: " + g.getBestFitness());
            }
        }
    }
}