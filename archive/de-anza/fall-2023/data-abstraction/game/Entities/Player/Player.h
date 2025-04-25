//
// Created by Connor Petri on 9/29/23.
//

#ifndef GAME_PLAYER_H
#define GAME_PLAYER_H
#include "../Entity.h"

namespace game {

    class Player : public Entity {
    public:
        Player(const std::string &name = "Player", int health = 100, int attack = 10, int defense = 10);
        Player(const Player &player);
        ~Player();

        void turn();

    protected:
        
    };

}


#endif //GAME_PLAYER_H
