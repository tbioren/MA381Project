#include <iostream>
#include <vector>
#include "Chromosome.h"
using namespace std;
#pragma once

class Generation {
private:
    vector<Chromosome> population;
    double mutationRate;
    int populationSize;
    int chromosomeSize;
    bool compareChromosomes(Chromosome c1, Chromosome c2);
    void createChromosomes(vector<Chromosome>& population, int populationSize, int chromosomeSize);
 public:
     Generation(int populationSize, int chromosomeSize, double mutationRate);
    ~Generation();
    void evolve();
    long getBestChromosome();
//     double getBestFitness();
//     double getAverageFitness();
//     double getWorstFitness();
};