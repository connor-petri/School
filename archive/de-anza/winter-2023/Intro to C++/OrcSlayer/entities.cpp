#include "entities.h"
#include "dice.h"

#include <vector>
#include <stdexcept>

using namespace game;


//--------------------------------------------------------------


Stat::Stat(std::string name, int value)
: _name(std::move(name)), _value(value), _modifier((value - 10) / 2) {}

std::string Stat::name() const { return this->_name; }
void Stat::name(std::string name) { this->_name = std::move(name); }

int Stat::value() const { return this->_value; }
void Stat::value(int value)
{
    this->_value = value;
    this->_modifier = (value - 10) / 2;
}

int Stat::modifier() const { return this->_modifier; }


//--------------------------------------------------------------


Entity::Entity(std::string name, int level, int maxHp, int ac, int str, int dex, int con)
        : _name(std::move(name)), _level(level), _hp(maxHp), _maxHp(maxHp), _ac(ac),
          _str(Stat("Str", str)),
          _dex(Stat("Dex", dex)),
          _con(Stat("Con", con)) {}


std::string Entity::name() const { return this->_name; }
void Entity::name(std::string name) { this->_name = std::move(name); }

int Entity::level() const { return this->_level; }
void Entity::level(int level) { this->_level = level; }

int Entity::hp() const { return this->_hp; }
void Entity::hp(int hp) { this->_hp = hp; }

int Entity::maxHp() const { return this->_maxHp; }
void Entity::maxHp(int maxHp) { this->_maxHp = maxHp; }

int Entity::ac() const { return this->_ac; }
void Entity::ac(int ac)
{
    this->_ac = ac;
    if (this->_ac < 0)
        this->_ac = 0;
}


Stat* Entity::str() { return &this->_str; }
Stat* Entity::dex() { return &this->_dex; }
Stat* Entity::con() { return &this->_con; }

std::vector<InvSlot*> *Entity::inventory()  { return &this->_inventory; }

//---
void Entity::invClean()
{

    for (int i = 0; i < this->_inventory.size(); i++)
    {
        if (this->_inventory.at(i)->isEmpty())
        {
            delete this->_inventory.at(i);
            this->_inventory.erase(this->_inventory.begin() + i);
        }

    }


}


void Entity::doDamage(int amount)
{
    this->_hp -= amount;

    if (this->_hp < 0)
        this->_hp = 0;
}

void Entity::heal(int amount)
{
    this->_hp += amount;
    if (this->_hp > this->_maxHp)
        this->_hp = this->_maxHp;
}


Item* Entity::getItem(Item* item)
{
    for (InvSlot* slot : this->_inventory)
    {
        if (item == slot->item())
        {
            return slot->item();
        }
    }

    return nullptr;
}


Item* Entity::getItem(int index)
{
    try
    {
        return this->_inventory.at(index)->item();
    }
    catch (std::out_of_range())
    {
        return nullptr;
    }
}


void Entity::addItem(Item* item, int quantity)
{

    for (int i = 0; i < inventory()->size(); i++)
    {
        if (inventory()->at(i)->item()->id() == item->id())
        {
            inventory()->at(i)->quantity(inventory()->at(i)->quantity() + quantity);
            return;
        }
    }

    inventory()->emplace_back(new InvSlot(item, quantity));

}


bool Entity::removeItem(Item* item, int quantity)
{

    for (InvSlot* slot : this->_inventory)
    {

        if (slot->item() == item)
        {
            slot->quantity(slot->quantity() - quantity);
            return true;
        }

    }

    return false;

}


int Entity::check(Stat* stat) { return dice::roll() + stat->modifier(); }

bool Entity::check(Stat* stat, int dc) { return (dice::roll() + stat->modifier() >= dc); }


void Entity::use(InvSlot* slot, Entity* target)
{

}


