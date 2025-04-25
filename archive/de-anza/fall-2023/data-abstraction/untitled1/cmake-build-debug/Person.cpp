//
// Created by Connor Petri on 9/7/24.
//

#include "Person.h"
#include <string>
#include <iostream>


Person::Person(std::string name, int age, float balance)
{
    this->name = name;
    this->age = age;
    this->balance = balance;
}

void Person::sayHi()
{
    std::cout << "Hi, my name is " << this->name << ". I am " << this->age << " years old.";
}