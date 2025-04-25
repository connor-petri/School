//
// Created by Connor Petri on 3/18/23.
//
#pragma once

#include <string>
#include <vector>
#include "items.h"

namespace game
{

    class Stat
    {

    private:
        std::string _name;
        int _value;
        int _modifier;

    public:
        explicit Stat(std::string name, int value);

        std::string name() const;
        void name(std::string name);

        int value() const;
        void value(int value);

        int modifier() const;

    };

    class Entity
    {

    private:

        std::string _name;
        int _level;
        int _hp;
        int _maxHp;
        int _ac;

        Stat _str;
        Stat _dex;
        Stat _con;

        std::vector<InvSlot*> _inventory;

    public:
        explicit Entity(std::string name = "Commoner", int level = 1, int maxHp = 1, int ac = 10,
                        int str = 10, int dex = 10, int con = 10);

        std::string name() const;
        void name(std::string name);

        int level() const;
        void level(int level);

        int hp() const;
        void hp(int hp);

        int maxHp() const;
        void maxHp(int maxHp);

        int ac() const;
        void ac(int ac);


        Stat* str();
        Stat* dex();
        Stat* con();

        std::vector<InvSlot*> *inventory();

        void invClean();

        void doDamage(int amount);
        void heal(int amount);

        Item* getItem(Item* item);
        Item* getItem(int index);
        void addItem(Item* item, int quantity = 1);
        bool removeItem(Item* item, int quantity = 1);

        int check(Stat* stat);
        bool check(Stat* stat, int dc);

        void use(InvSlot* slot, Entity* target);
        void use(Item* item, Entity* target);

    };

}