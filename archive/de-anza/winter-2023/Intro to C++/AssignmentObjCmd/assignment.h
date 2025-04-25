#pragma once

#include <string>

namespace assignment
{


class Assignment
{

private:
    std::string variable;
    int value;
    
public:
    Assignment(std::string variable = "count", int value = 10);

    bool const operator==(const Assignment &rhs);
    bool const operator==(const int rhs);
    bool const operator<(const Assignment &rhs);
    bool const operator<(const int rhs);
    bool const operator>(const Assignment &rhs);
    bool const operator>(const int rhs);

    std::string getVar(void);
    int getVal(void);

};


void addAssignment(std::vector<Assignment> &assignments);
void listAssignments(std::vector<Assignment> &assignments);
void stats(std::vector<Assignment> &assignments);

}