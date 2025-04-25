//
// Created by Connor Petri on 6/1/23.
//

#include "Job.h"

std::istream& operator>>(std::istream& in, Job& rhs)
{
    int number;
    float hours, rate;

    std::cout << "Enter Job Number: ";
    in >> number;
    std::cout << "Hours to complete: ";
    in >> hours;
    std::cout << "Rate per hour: ";
    in >> rate;

    rhs.setNumber(number);
    rhs.setHours(hours);
    rhs.setRate(rate);

    return in;

}

std::ostream& operator<<(std::ostream& out, const Job& rhs)
{
    out << "Job # " << rhs.getNumber() << " " << rhs.getHours() << " hours at $" << rhs.getRate() << " per hour";
    return out;
}

float Job::operator+(const Job& rhs)
{
    return this->_hours + rhs.getHours();
}

float Job::operator-(const Job& rhs)
{
    return this->_hours - rhs.getHours();
}