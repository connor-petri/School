//
// Created by Connor Petri on 6/23/23.
//

#ifndef GAME__DICE_H
#define GAME__DICE_H

#include <random>
#include <ctime>

namespace game::dice
{

    int roll(int min, int max);
    float roll(float min, float max);

}


#endif //GAME__DICE_H
