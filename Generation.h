#include <iostream>
using namespace std;
#pragma once

class Generation {
private:
    double mutationRate;
    unsigned long long generation[];
public:
    Generation(double mutationRate);
    ~Generation();
    void evolve();
    long getBestChromosome();
    double getBestFitness();
    double getAverageFitness();
    double getWorstFitness();
};