//
// Created by Connor Petri on 6/24/23.
//

#include "Inventory.h"

namespace game::items
{

    // protected:
    bool InvSlot::checkEmpty()
    {
        if (*this->_quantity <= 0)
        {
            *this->_quantity = 0;
            *this->_isEmpty = true;
        }
        else { *this->_isEmpty = false; }

        return *this->_isEmpty;
    }

    // public:
    InvSlot::InvSlot(const Item& item, int quantity)
    {
        this->_item = new Item(item);
        if (!checkEmpty())
        {
            *this->_quantity = quantity;
        }
    }

    InvSlot::~InvSlot()
    {
        delete this->_item;
        delete this->_quantity;
        delete this->_isEmpty;
    }


    InvSlot* Inventory::find(const Item& item) const
    {
        for (auto slot : *this->_slots)
        {
            if (slot->item() == item)
                return slot;
        }

        return nullptr;
    }

    Inventory::Inventory(const Inventory& inv)
    {
        for (auto slot : *inv._slots)
        {
            this->_slots->push_back(new InvSlot(*slot));
        }
    }

    Inventory::~Inventory()
    {
        for (auto slot : *this->_slots)
        {
            delete slot;
        }
        delete this->_slots;
    }

    void Inventory::addItem(const Item& item, int quantity)
    {
        InvSlot* ptr = find(item);
        if (ptr == nullptr)
        {
            this->_slots->push_back(new InvSlot(item, quantity));
        }
        else
        {
            ptr->changeQuantity(quantity);
        }
    }

} // game