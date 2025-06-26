//
// Created by Connor Petri on 9/29/23.
//
#include "EntityTests.h"
#include <exception>

namespace game {

    void entityTests() {
        Entity e("Entity1", 100, 10, 10);

        // Constructor test
        if (e.getName() != "Entity1") {
            throw std::runtime_error("Entity name not set correctly in constructor.");
        }

        if (e.getHealth() != 100) {
            throw std::runtime_error("Entity health not set correctly in constructor.");
        }

        if (e.getMaxHealth() != 100) {
            throw std::runtime_error("Entity max health not set correctly in constructor.");
        }

        if (e.getAttack() != 10) {
            throw std::runtime_error("Entity attack not set correctly in constructor.");
        }

        if (e.getDefense() != 10) {
            throw std::runtime_error("Entity defense not set correctly in constructor.");
        }

        // Copy constructor test
        Entity e2(e);

        if (e2.getName() != "Entity1") {
            throw std::runtime_error("Entity name not set correctly in copy constructor.");
        }

        if (e2.getHealth() != 100) {
            throw std::runtime_error("Entity health not set correctly in copy constructor.");
        }

        if (e2.getMaxHealth() != 100) {
            throw std::runtime_error("Entity max health not set correctly in copy constructor.");
        }

        if (e2.getAttack() != 10) {
            throw std::runtime_error("Entity attack not set correctly in copy constructor.");
        }

        if (e2.getDefense() != 10) {
            throw std::runtime_error("Entity defense not set correctly in copy constructor.");
        }

        // setters test
        e2.setName("Entity2");
        e2.setHealth(200);
        e2.setMaxHealth(200);
        e2.setAttack(20);
        e2.setDefense(20);

        if (e2.getName() != "Entity2") {
            throw std::runtime_error("Entity name not set correctly in setters.");
        }

        if (e2.getHealth() != 200) {
            throw std::runtime_error("Entity health not set correctly in setters.");
        }

        if (e2.getMaxHealth() != 200) {
            throw std::runtime_error("Entity max health not set correctly in setters.");
        }

        if (e2.getAttack() != 20) {
            throw std::runtime_error("Entity attack not set correctly in setters.");
        }

        if (e2.getDefense() != 20) {
            throw std::runtime_error("Entity defense not set correctly in setters.");
        }

        // takeDamage test
        e2.takeDamage(30);

        if (e2.getHealth() != 190) {
            throw std::runtime_error("Entity health not set correctly in takeDamage.");
        }

    }

}