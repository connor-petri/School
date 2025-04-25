//
// Created by Connor Petri on 5/16/23.
//

#ifndef INC_13_1_EMPLOYEEINFO_H
#define INC_13_1_EMPLOYEEINFO_H

#include <string>

class EmployeeInfo
{

private:
    std::string _name;
    float _hourlyRate;
    float _numHours;

public:
    EmployeeInfo();

    // I use function overloading for my getters and setters
    // I find it makes for cleaner and easier to read code.

    // Getters
    std::string name() const;
    float hourlyRate() const;
    float numHours() const;

    // Setters
    void name(const std::string& name);
    void hourlyRate(const float rate);
    void numHours(const float hours);

    float calculatePay() const;

};


#endif //INC_13_1_EMPLOYEEINFO_H
