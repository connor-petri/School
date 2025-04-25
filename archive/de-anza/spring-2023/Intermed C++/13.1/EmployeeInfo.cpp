//
// Created by Connor Petri on 5/16/23.
//

#include "EmployeeInfo.h"


EmployeeInfo::EmployeeInfo()
: _name(""), _hourlyRate(0.0f), _numHours(0.0f) {}


// Getters
std::string EmployeeInfo::name() const
{
    return this->_name;
}

float EmployeeInfo::hourlyRate() const
{
    return this->_hourlyRate;
}

float EmployeeInfo::numHours() const
{
    return this->_numHours;
}

// Setters
void EmployeeInfo::name(const std::string& name)
{
    this->_name = name;
}

void EmployeeInfo::hourlyRate(const float rate)
{
    this->_hourlyRate = rate;
}

void EmployeeInfo::numHours(const float hours)
{
    this->_numHours = hours;
}

float EmployeeInfo::calculatePay() const
{
    return this->_numHours * this->_hourlyRate;
}