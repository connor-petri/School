//
// Created by Connor Petri on 9/29/23.
//

#pragma once

#include <string>

namespace game {

    class Entity {
    public:
        Entity(const std::string &name = "Entity", int health = 100, int attack = 10, int defense = 10);
        Entity(const Entity &entity);
        ~Entity();

        std::string& getName() const { return *_name; }
        int getHealth() const { return *_health; }
        int getMaxHealth() const { return *_maxHealth; }
        int getAttack() const { return *_attack; }
        int getDefense() const { return *_defense; }

        void setName(const std::string &name) { *_name = name; }
        void setHealth(int health) { *_health = health; }
        void setMaxHealth(int maxHealth) { *_maxHealth = maxHealth; }
        void setAttack(int attack) { *_attack = attack; }
        void setDefense(int defense) { *_defense = defense; }

        void takeDamage(unsigned int damage);

    protected:
        std::string *_name = new std::string;

        int *_health = new int;
        int *_maxHealth = new int;

        int *_attack = new int;
        int *_defense = new int;

//        virtual void turn() = 0;

    };

}
