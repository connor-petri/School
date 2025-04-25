//
// Created by Connor Petri on 6/24/23.
//

#include "Item.h"

namespace game::items
{

    Item::Item(const string& name, const string& desc, int value)
    {
        *this->_name = name;
        *this->_desc = desc;
        *this->_value = value;
    }

    Item::Item(const Item& item)
    {
        *this->_name = item.name();
        *this->_desc = item.desc();
        *this->_value = item.value();
    }

    Item::~Item()
    {
        delete this->_name;
        delete this->_desc;
        delete this->_value;
    }

    bool Item::operator==(const Item& rhs) const
    {
        return
        this->_name == rhs._name &&
        this->_desc == rhs._desc &&
        this->_value == rhs._value;
    }

    Weapon::Weapon(const string &name, const string &desc, int value, int dmgLower, int dmgUpper, int hitChance)
    : Item(name, desc, value), _damage(new Range(dmgLower, dmgUpper))
    {
        *this->_hitChance = hitChance;
    }

    Weapon::Weapon(const Weapon& item)
    : Item(item.name(), item.desc(), item.value()), _damage(new Range(item.damageLower(), item.damageUpper()))
    {
        *this->_hitChance = item.hitChance();
    }

    Weapon::~Weapon()
    {
        delete this->_name;
        delete this->_desc;
        delete this->_value;
        delete this->_damage;
        delete this->_hitChance;
    }

    bool Weapon::operator==(const Weapon& rhs) const
    {
        return  Item::operator==(rhs) && this->_damage == rhs._damage &&
                this->_hitChance == rhs._hitChance;

    }

    bool Weapon::use(Entity& user, Entity& target)
    {
        bool doesHit = *this->_hitChance <= roll(1, 100);
        int damage = roll(this->_damage->lower, this->_damage->upper);

        if (doesHit)
        {
            std::cout << user.name() << " attacks " << target.name() << " with a " << *this->_name << ", dealing " << damage << " damage!\n";
            target.takeDamage(damage);
        }
        else
        {
            std::cout << user.name() << " attacks " << target.name() << " with a " << *this->_name << ", but missed!\n";
        }

        return doesHit;
    }

} // game