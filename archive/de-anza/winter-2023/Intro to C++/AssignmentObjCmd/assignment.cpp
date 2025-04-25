#include "assignment.h"

#include <string>
#include <vector>
#include <iostream>
#include <iomanip>

using namespace assignment;


//Class member functions
Assignment::Assignment(std::string newVariable, int newValue)
    : variable(newVariable), value(newValue) {}


bool const Assignment::operator==(const Assignment &rhs)
{
    return (this->value == rhs.value);
}

bool const Assignment::operator==(const int rhs)
{
    return (this->value == rhs);
}

bool const Assignment::operator<(const Assignment &rhs)
{
    return (this->value < rhs.value);
}

bool const Assignment::operator<(const int rhs)
{
    return (this->value < rhs);
}

bool const Assignment::operator>(const Assignment &rhs)
{
    return (this->value > rhs.value);
}

bool const Assignment::operator>(const int rhs)
{
    return (this->value > rhs);
}


std::string Assignment::getVar(void)
{
    return this->variable;
}

int Assignment::getVal(void)
{
    return this->value;
}


// Other Functions
void addAssignment(std::vector<Assignment> &assignments)
{

    std::string newVar;
    char sign;
    int newVal;
    bool isValid = true;

    do
    {

        std::cout << "Please enter an assignment: ";
        std::cin >> newVar >> sign >> newVal;

        if (sign != '=')
        {
            std::cout << "Invalid assignment operator.\n";
            isValid = false;
        }
        
        for (int i=0; i < newVar.length(); i++)
        {
            if (newVar[i] >= 48 && newVar[i] <= 57)
            {
                std::cout << "Invalid variable name.\n";
                isValid = false;
                break;
            }

        }

        if (std::cin.fail())
        {
            std::cout << "Assignment variable is not a number.\n";
            std::cin.clear();
            std::cin.ignore(INT32_MAX);
            isValid = false;
        }

    } 
    while (!isValid);

    assignments.push_back(Assignment(newVar, newVal));

}


void listAssignments(std::vector<Assignment> &assignments)
{

    if (assignments.size() == 0)
    {
        std::cout << "There is no assignment.\n";
        return;
    }

    std::cout << "All assignments:\n";

    for (int i=0; i < assignments.size(); i++)
    {
        std::cout << std::setw(7) << assignments.at(i).getVal() << std::setw(7) << "=" << std::setw(7) << assignments.at(i).getVar() << "\n";
    }

}


void stats(std::vector<Assignment> &assignments)
{

    int numAssignments = assignments.size();

    if (numAssignments == 0)
    {
        std::cout << "There is no assignment.\n";
        return;
    }

    int positive = 0;
    int negative = 0;
    Assignment largest;
    Assignment smallest;

    for (int i=0; i < numAssignments; i++)
    {

        if (assignments.at(i) > 0)
        {
            positive++;
        }

        if (assignments.at(i) < 0)
        {
            negative++;
        }

        if (i == 0)
        {
            largest = assignments.at(i);
            smallest = assignments.at(i);
            continue;
        }

        if (assignments.at(i) > largest)
        {
            largest = assignments.at(i);
        }

        if (assignments.at(i) < smallest)
        {
            smallest = assignments.at(i);
        }

    }

    std::cout << std::setw(10) << "Total number of assignments: " << numAssignments << "\n"
              << std::setw(10) << "Total number of positive assignments: " << positive << "\n"
              << std::setw(10) << "Total number of negative assignments: " << negative << "\n"
              << std::setw(10) << "The largest assignment: " << std::setw(7) << largest.getVar() << std::setw(7) << "=" << std::setw(7) << largest.getVal() << "\n"
              << std::setw(10) << "The smallest assignment: " << std::setw(7) << smallest.getVal() << std::setw(7) << "=" << std::setw(7) << smallest.getVal() << "\n";

}