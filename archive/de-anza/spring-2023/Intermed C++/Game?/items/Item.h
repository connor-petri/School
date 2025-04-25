//
// Created by Connor Petri on 6/24/23.
//

#ifndef GAME__ITEM_H
#define GAME__ITEM_H

#include <string>

#include "../entities/Entity.h"

using std::string;
using  namespace game::entities;

namespace game::items
{

    class Range
    {
    public:
        int lower;
        int upper;
        Range(int lower, int upper) : lower(lower), upper(upper) {}
        bool operator==(const Range& rhs) const
        {
            return lower == rhs.lower && upper == rhs.upper;
        }
    };

    class Item
    {
    protected:
        string* _name = new string;
        string* _desc = new string;
        int* _value = new int;

    public:
        explicit Item(const string& name = "Unnamed Item", const string& desc = "", int value = 0);
        Item(const Item& item);
        ~Item();

        virtual bool operator==(const Item& rhs) const;

        string name() const { return *this->_name; }
        string desc() const { return *this->_desc; }
        int value() const { return *this->_value; }

        void setName(const string& name) { *this->_name = name; }
        void setDesc(const string& desc) { *this->_desc = desc; }

        virtual bool use() { return false; }
    };


    class Weapon : public Item
    {
    protected:
        Range* _damage;
        int* _hitChance = new int;

    public:
        Weapon(const string& name = "Unnamed Weapon", const string& desc = "", int value = 0, int dmgLower = 0, int dmgUpper = 0, int hitChance = 0);
        Weapon(const Weapon& item);
        ~Weapon();

        bool operator==(const Item& rhs) const override;

        Range damage() const { return *this->_damage; }
        int damageLower() const { return this->_damage->lower; }
        int damageUpper() const { return this->_damage->upper; }
        int hitChance() const { return *this->_hitChance; }

        void setDamageLower(int dmg) { this->_damage->lower = dmg; }
        void setDamageUpper(int dmg) { this->_damage->upper = dmg; }

        bool use(Entity& user, Entity& target) override;
    };


} // game

#endif //GAME__ITEM_H
