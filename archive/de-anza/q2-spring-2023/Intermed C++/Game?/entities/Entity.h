//
// Created by Connor Petri on 6/23/23.
//

#ifndef GAME__ENTITY_H
#define GAME__ENTITY_H

#include <string>
#include <iostream>

#include "../Dice.h"

using std::string;
using game::dice::roll;

namespace game::entities
{

    class Entity
    {
    protected:
        string* _name = new string;
        int* _hp = new int;
        int* _maxHp = new int;

    public:
        Entity(const string& name, int maxHp);
        Entity(const string& name, int maxHp, int startingHp);
        ~Entity();

        string name() const { return *this->_name; }
        int hp() const { return *this->_hp; }
        int maxHp() const { return *this->_maxHp; }

        void setName(const string& name) { *this->_name = name; }
        void setHp(const int hp) { *this->_hp = hp; }
        void setMaxHp(const int maxHp) { *this->_maxHp = maxHp; }

        virtual void takeDamage(int damage) { *this->_hp -= damage; }
        virtual void turn() {}
    };

}

#endif //GAME__ENTITY_H
