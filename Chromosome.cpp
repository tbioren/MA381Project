#include "Chromosome.h"
#include "Main.h"
#include <bitset>
using namespace std;

Chromosome::Chromosome(unsigned int sizeIn) {
    this->size = sizeIn;
    this->byteSize = (sizeIn + 7) / 8;
    this->generation = new unsigned char[byteSize];
    for(unsigned int i = 0; i < size; i++) {
        generation[i] = 0;
    }
    this->fitness = 0;
}

Chromosome::~Chromosome() {
    delete[] generation;
}

void Chromosome::mutate(double mutationRate) {
    srand(time(NULL));
    for(unsigned int i = 0; i < byteSize; i++) {
        for(unsigned int j = 0; j < 8; j++) {
            generation[i] ^= (((double)rand() / RAND_MAX) < mutationRate ? 0 : 1) << j;
        }
    }
}

double Chromosome::updateFitness() {
    fitness = 0;
    for(unsigned int i = 0; i < byteSize; i++) {
        unsigned char n = generation[i];
        while(n) {
            fitness += (int)n & 1;
            n >>= 1;
        }
    }
    return fitness;
}

double Chromosome::getFitness() {
    return fitness;
}