//
// Created by Connor Petri on 6/23/23.
//

#include "Dice.h"

namespace game::dice
{

    int roll(int min, int max)
    {
        static std::default_random_engine rng(time(nullptr));
        std::uniform_int_distribution<int> dist(min, max);
        return dist(rng);
    }

    float roll(float min, float max)
    {
        static std::default_random_engine rng(time(nullptr));
        std::uniform_real_distribution<float> dist(min, max);
        return dist(rng);
    }

}