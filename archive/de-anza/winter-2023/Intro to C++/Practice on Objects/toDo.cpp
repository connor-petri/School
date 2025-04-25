#include "toDo.h"
#include <string>
#include <cmath>


TodoTask::TodoTask(std::string name, int month, int day)
    : name(name) , month(month), day(day) {}


bool TodoTask::operator==(const TodoTask &task2)
{
    return (this->month == task2.month && this->day == task2.day);
}


bool TodoTask::operator<(const TodoTask &task2)
{
    if (this->month < task2.month)
        return true;

    else if (this->month > task2.month)
        return false;

    if (this->day < task2.day)
        return true;
    
    else
        return false;
}


bool TodoTask::operator>(const TodoTask &task2)
{
    if (*this == task2 || *this < task2)
        return false;

    else    
        return true;
}


std::string TodoTask::toString(void)
{

    return "TASK(" + this->name + ") DATE(" + std::to_string(this->month) + "/" + std::to_string(this->day) + ")";

}