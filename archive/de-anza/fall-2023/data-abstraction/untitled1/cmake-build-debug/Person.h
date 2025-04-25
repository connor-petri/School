//
// Created by Connor Petri on 9/7/24.
//

#include <string>

#ifndef UNTITLED1_PERSON_H
#define UNTITLED1_PERSON_H


class Person
{
private:
    std::string name;
    int age;
    float balance;

public:
    Person(std::string name, int age, float balance);

    void sayHi();
};


#endif //UNTITLED1_PERSON_H
