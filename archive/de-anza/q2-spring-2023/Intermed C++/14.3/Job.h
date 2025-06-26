//
// Created by Connor Petri on 6/1/23.
//

#ifndef INC_14_3_JOB_H
#define INC_14_3_JOB_H

#include <iostream>

class Job
{

private:
    int _number;
    float _hours;
    float _rate;

public:
    Job(int number = 0, float hours = 0.0f, float rate = 0.0f)
    : _number(number), _hours(hours), _rate(rate) {}

    int getNumber() const { return this->_number; }
    float getHours() const { return this->_hours; }
    float getRate() const { return this->_rate; }

    void setNumber(const int number) { this->_number = number; }
    void setHours(const float hours) { this->_hours = hours; }
    void setRate(const float rate) { this->_rate = rate; }

    friend std::istream& operator>>(std::istream& in, Job& rhs);
    friend std::ostream& operator<<(std::ostream& out, const Job& rhs);

    float operator+(const Job& rhs);
    float operator-(const Job& rhs);

};


#endif //INC_14_3_JOB_H
