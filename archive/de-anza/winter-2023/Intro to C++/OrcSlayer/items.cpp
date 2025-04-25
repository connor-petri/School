#include "items.h"
#include "dice.h"
#include <string>
#include <stdexcept>

using namespace game;


//Item functionality----------------------------------------------------------

Item::Item(std::string name, std::string desc, int id)
:   _name(std::move(name)), _desc(std::move(desc)), _id(id) { }

// Item operator overloading
bool Item::operator==(Item* rhs) const
{
    return (this->_id == rhs->id());
}

bool Item::operator!=(Item* rhs) const
{
    return !(this == rhs);
}

// getters and setters
std::string Item::name() const { return this->_name; }
void Item::name(std::string name) { this->_name = std::move(name); }

std::string Item::desc() const { return this->_desc; }
void Item::desc(std::string desc) { this->_desc = std::move(desc); }

int Item::id() const { return this->_id; }


//Invslot Functionality--------------------------------------------------------------


InvSlot::InvSlot() : _item(nullptr), _quantity(0) {}

InvSlot::InvSlot(Item* item, int quantity) : _item(item), _quantity(quantity) {}

InvSlot::~InvSlot() { delete this->_item; }


Item* InvSlot::item() const { return this->_item; }

void InvSlot::item(Item* item)
{
    delete this->_item;
    this->_item = item;
}

int InvSlot::quantity() const { return this->_quantity; }

void InvSlot::quantity(int quantity)
{
    this->_quantity = quantity;
    if (this->_quantity <= 0)
    {
        this->_quantity = 0;
        this->_isEmpty = true;
    }
    else
    {
        this->_isEmpty = false;
    }
}

bool InvSlot::isEmpty() const { return this->_isEmpty; }


//Weapon functionality----------------------------------------------------------


Weapon::Weapon(std::string name, std::string desc, int id, std::string diceString, std::string dType, std::string rollStat)
: _diceString(std::move(diceString)), _dType(std::move(dType)), _rollStat(std::move(rollStat))
{
    if (rollStat != "str" || rollStat != "dex" || rollStat != "con")
        throw std::runtime_error("Invalid rollStat initialized in Weapon object");
}

// getters and setters.
std::string Weapon::diceString() const { return this->_diceString; }
void Weapon::diceString(std::string diceString) { this->_diceString = std::move(diceString); }

std::string Weapon::dType() const { return this->_diceString; }
void Weapon::dType(std::string dType) { this->_dType = std::move(dType); }

//---

int Weapon::rollDamage() const
{
    return dice::roll(this->_diceString);
}


void Weapon::use(auto* target, auto* user)
{
    try
    {
        if (this->_rollStat == "str")


        int damage = rollDamage();
        std::cout << user->name() << " attacks " << target
        target->hp(target->hp() - damage);
    }
    catch (std::exception())
    {
        throw std::runtime_error("Target and user must be of type Entity.");
    }

}


