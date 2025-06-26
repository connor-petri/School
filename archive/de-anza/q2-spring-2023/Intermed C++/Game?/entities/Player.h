//
// Created by Connor Petri on 6/24/23.
//

#ifndef GAME__PLAYER_H
#define GAME__PLAYER_H

#include "Entity.h"
#include "../items/Item.h"
#include "../items/Inventory.h"

using std::vector, std::string;
using namespace game::items;

namespace game::entities
{

    class Player
    {
    private:
        Inventory* _inv = new Inventory;

    public:
        Player(string name = "Player", int hp = 100);

    };

} // game

#endif //GAME__PLAYER_H
