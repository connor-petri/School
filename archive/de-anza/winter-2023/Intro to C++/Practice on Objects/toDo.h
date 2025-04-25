#pragma once

#include <string>


class TodoTask
{
private:
    std::string name;
    int month;
    int day;

public:
    TodoTask(std::string name, int month, int day);

    bool operator==(const TodoTask &task2);
    bool operator<(const TodoTask &task2);
    bool operator>(const TodoTask &task2);

    std::string toString(void);


};