//
// Created by Connor Petri on 6/23/23.
//

#include "Entity.h"

namespace game::entities
{

    Entity::Entity(const string& name, int hp)
    {
        *this->_name = name;
        *this->_hp = hp;
        *this->_maxHp = hp;
    }

    Entity::Entity(const string& name, int maxHp, int startingHp)
    {
        *this->_name = name;
        *this->_hp = maxHp;
        *this->_maxHp = startingHp;
    }

    Entity::~Entity()
    {
        delete this->_name;
        delete this->_hp;
        delete this->_maxHp;
    }


}