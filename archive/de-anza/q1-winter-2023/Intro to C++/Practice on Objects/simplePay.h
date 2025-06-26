#pragma once

#include <string>


class SimplePay
{

private:
    int hours;
    double rate;
    double totalPay;

public:
    SimplePay(int hours, double rate);

    std::string toString(void);
    bool payMore(SimplePay &pay2);

};