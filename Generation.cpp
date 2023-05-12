#include "Generation.h"
using namespace std;

#define _POPULATION_SIZE_ 100

Generation::Generation(double mutationRate) {
    this->mutationRate = mutationRate;
    unsigned long *generation = new unsigned long[_POPULATION_SIZE_];
    srand(time(NULL));
    for(int i = 0; i < _POPULATION_SIZE_; i++) {
        generation[i] = 0;
        for(int j = 0; j < 32; j++) {
            generation[i] = generation[i] << 1;
            generation[i] = generation[i] | (rand() % 2);
        }
    }
}

Generation::~Generation() {
    delete[] generation;
}

void Generation::evolve() {
    srand(time(NULL));
    for(int i = 0; i < _POPULATION_SIZE_; i++) {
        for(int j=0; j < 64; j++) {
            unsigned int foo = (((double)rand() / RAND_MAX) < mutationRate ? 0 : 1);
            foo = foo << j;
            generation[i] ^= foo;
        }
    }
}

long Generation::getBestChromosome() {
    return generation[0];
}