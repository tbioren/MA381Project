import java.util.Arrays;

public class Chromosome implements Comparable<Chromosome>{
    private char[] genes;
    private double fitness;
    private static final int[] SPECIAL_NUMBERS = {1,2,4,8,16,32,64,128,256,512,1024};
    private int setBits;

    public Chromosome(char[] genes) {
        this.genes = Arrays.copyOf(genes, genes.length);
    }

    public Chromosome(int length) {
        genes = new char[(length+7)/8];
        for(int i=0; i < genes.length; i++) {
            genes[i] = 0;
        }
        // for(int i = 0; i < genes.length; i++) {
        //     genes[i] = (char)(Math.random() * 256);
        // }
    }

    public char[] getGenes() {
        return genes;
    }

    public double getFitness() {
        return fitness;
    }

    // Currently fittest gene is one that is all 1s
    // public double updateFitness() {
    //     int totalSet = 0;
    //     for(int i=0; i < genes.length; i++) {
    //         totalSet += countSetBits(genes[i]);
    //     }
    //     fitness = (double)totalSet / (genes.length * 8);
    //     return fitness;
    // }

    public double updateFitness() {
        setBits = 0;
        for(int i=0; i < genes.length; i++) {
            setBits += countSetBits(genes[i]);
        }
        int distToSpecialNum = Integer.MAX_VALUE;
        for(int specialNum : SPECIAL_NUMBERS) {
            if(distToSpecialNum > Math.abs(specialNum - setBits)) distToSpecialNum = Math.abs(specialNum - setBits);
        }
        fitness = setBits - distToSpecialNum;
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    private int countSetBits(char n) {
        if(n == 0) return 0;
        return (n & 1) + countSetBits((char) (n >> 1));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < genes.length; i++) {
            sb.append(String.format("%8s", Integer.toBinaryString(genes[i])).replace(' ', '0'));
        }
        return sb.toString();
    }

    public int compareTo(Chromosome other) {
        return Double.compare(fitness, other.fitness);
    }

    public void mutate(double mutationRate) {
        for(int i=0; i < genes.length; i++) {
            for(int j=0; j < 8; j++) {
                if(Math.random() < mutationRate) {
                    genes[i] ^= 1 << j;
                }
            }
        }
    }
}