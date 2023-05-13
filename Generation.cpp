#include "Generation.h"
#include "Chromosome.h"
#include <time.h>
#include <algorithm>
using namespace std;

Generation::Generation(int populationSize, int chromosomeSize, double mutationRate) {
    this->mutationRate = mutationRate;
    this->populationSize = populationSize;
    this->chromosomeSize = chromosomeSize;
    vector<Chromosome> population;
    createChromosomes(population, populationSize, chromosomeSize);
    cout << "Generation created with " << populationSize << " chromosomes of size " << chromosomeSize << endl;
}

Generation::~Generation() {
    cout << "Generation destroyed" << endl;
}

void Generation::createChromosomes(vector<Chromosome>& population, int populationSize, int chromosomeSize) {
    srand(time(NULL));
    for(int i = 0; i < populationSize; i++) {
        Chromosome c();
        population.push_back(c);
    }
}

bool Generation::compareChromosomes(Chromosome c1, Chromosome c2) {
    return c1.getFitness() > c2.getFitness();
}