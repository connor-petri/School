//
// Created by Connor Petri on 3/18/23.
//

#pragma once

#include <string>
#include <vector>

namespace game
{

    class Item
    {

    private:
        std::string _name;
        std::string _desc;
        const int _id;

    public:
        explicit Item(std::string name = "Default Item", std::string desc = "", int id = -1);

        bool operator==(Item* rhs) const;
        bool operator!=(Item* rhs) const;

        std::string name() const;
        void name(std::string name);

        std::string desc() const;
        void desc(std::string desc);

        int id() const;

    };

    //--------------------------------------------------------------

    class InvSlot
    {

    private:
        Item* _item;
        int _quantity;
        bool _isEmpty;

    public:
        InvSlot();
        explicit InvSlot(Item* item, int quantity = 0);
        ~InvSlot();

        Item* item() const;
        void item(Item* item);
        int quantity() const;
        void quantity(int quantity);
        bool isEmpty() const;

    };

    //--------------------------------------------------------------

    class Weapon : public Item
    {

    private:
        std::string _diceString;
        std::string _dType;
        std::string _rollStat;

    public:
        explicit Weapon(std::string name = "Default Item", std::string desc = "", int id = -1,
                        std::string diceString = "1d6", std::string dType = "slashing", std::string rollStat = "str");

        std::string diceString() const;
        void diceString(std::string diceString);

        std::string dType() const;
        void dType(std::string dType);

        int rollDamage() const;

        void use(auto* target, auto* user);

    };

}