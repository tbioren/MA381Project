#include <iostream>
#include <bitset>
#include "Main.h"
#include "Chromosome.h"

using namespace std;

#define _IDEAL_CHROMOSOME_ 0x00000000

int main() {
    Chromosome c(10);
    cout << c.updateFitness() << endl;
    c.mutate(0.5);
    cout << c.updateFitness() << endl;
}