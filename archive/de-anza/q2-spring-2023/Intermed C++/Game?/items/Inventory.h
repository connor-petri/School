//
// Created by Connor Petri on 6/24/23.
//

#ifndef GAME__INVENTORY_H
#define GAME__INVENTORY_H

#include <vector>
#include "Item.h"
#include <fstream>

using std::vector;

namespace game::items
{

    class InvSlot
    {
    protected:
        Item* _item;
        int* _quantity = new int;
        bool* _isEmpty = new bool;

        bool checkEmpty();

    public:
        InvSlot(const InvSlot& item);
        InvSlot(const Item& item, int quantity = 0);
        ~InvSlot();

        bool operator==(const InvSlot& rhs) const { return this->_item == rhs._item; }

        Item item() const { return *this->_item; }
        int quantity() const { return *this->_quantity; }
        bool isEmpty() const { return *this->_isEmpty; }

        void setItem(const Item& item) { *this->_item = item; }

        void setQuantity(int q)
        {
            *this->_quantity = q;
            checkEmpty();
        }

        void changeQuantity(int i)
        {
            *this->_quantity += i;
            checkEmpty();
        }

    };

    class Inventory
    {
    private:
        InvSlot* find(const Item& item) const;

    protected:
        vector<InvSlot*>* _slots = new vector<InvSlot*>;

    public:
        Inventory() = default;
        Inventory(const Inventory& inv);
        ~Inventory();

        InvSlot slot(int index) { return *this->_slots->at(index); }

        void addItem(const Item& item, int quantity = 1);
    };

} // game

#endif //GAME__INVENTORY_H
