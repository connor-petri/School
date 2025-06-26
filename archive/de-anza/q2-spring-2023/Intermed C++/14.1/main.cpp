#include <iostream>

#define YEAR 2023

class Salesperson;

class Sale
{

private:
    int _day;
    int _month;
    float _total;
    int _spId;

public:
    Sale(int day, int month, float total, int spId)
            : _day(day), _month(month), _total(total), _spId(spId) {}

    void display(const Salesperson&);

};

class Salesperson
{

private:
    int _spId;
    std::string _lastName;

public:
    Salesperson(int spId, const std::string& lastName)
    : _spId(spId), _lastName(lastName) {}

    friend void Sale::display(const Salesperson&);

};

void Sale::display(const Salesperson& salesperson)
{
    std::cout << "Sale #" << this->_spId << " on " << this->_month << "/" << this->_day << "/" << YEAR << " for $" << this->_total << " sold by #" << salesperson._spId << " " << salesperson._lastName << "\n";
}

int main()
{

    Sale sale1(25, 12, 559.95, 103), sale2(15, 11, 359.95, 106);
    Salesperson sp1(103, "Woods"), sp2(106, "Hansen");

    sale1.display(sp1);
    sale2.display(sp2);

    return 0;
}
