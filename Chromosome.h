#include "Generation.h"
#include "Main.h"
using namespace std;
#pragma once

class Chromosome {
private:
    unsigned int size;
    unsigned int byteSize;
    unsigned char *generation;
    double fitness;
public:
    Chromosome(unsigned int sizeIn);
    ~Chromosome();
    void mutate(double mutationRate);
    double updateFitness();
    double getFitness();
};