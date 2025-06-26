//
// Created by Connor Petri on 9/29/23.
//

#include "Entity.h"

using namespace game;

Entity::Entity(const std::string &name, int health, int attack, int defense) {
    *_name = name;
    *_health = health;
    *_maxHealth = health;
    *_attack = attack;
    *_defense = defense;
}

Entity::Entity(const Entity &entity) {
    *_name = *entity._name;
    *_health = *entity._health;
    *_maxHealth = *entity._maxHealth;
    *_attack = *entity._attack;
    *_defense = *entity._defense;
}

Entity::~Entity() {
    delete _name;
    delete _health;
    delete _maxHealth;
    delete _attack;
    delete _defense;
}

void Entity::takeDamage(unsigned int damage) {
    if (damage > *_defense) {
        *_health -= ((int)damage - *_defense);
    }
}