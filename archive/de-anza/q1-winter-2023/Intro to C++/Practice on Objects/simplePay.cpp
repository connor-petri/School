#include "simplePay.h"
#include <string>


SimplePay::SimplePay(int hours, double rate)
    : hours(hours), rate(rate)
{

    if (this->hours <= 40)
        this->totalPay = rate * (double)hours;

    else
        this->totalPay = (rate * (double)hours) + ((hours - 40) * (rate * 1.5));

}


std::string SimplePay::toString(void)
{
    return "HOURS (" + std::to_string(this->hours) + ") PAY RATE(" + std::to_string(this->rate) + ") PAY AMOUNT(" + std::to_string(this->totalPay) + ")";
}

bool SimplePay::payMore(SimplePay &pay2)
{
    return (this->totalPay > pay2.totalPay);
}