#include "toDo.h"
#include "simplePay.h"

#include <string>
#include <iostream>

/* 

PLEASE READ: 

For TodoTask's member functions isLater() and isOnTheSameDate() I have
implemented those features using operator overloading rather than a memeber
function. I did this as I'm am very familiar with the basics of OOP and I wanted
more experience using operator overloading as I didn't learn how to do that in
python. 

operator== holds the isOnTheSameDate() functionality
operator> holds the isLater() functionality
operator< is what an isEarlier() function would be

Sorry for the inconvenience, I just wanted to practice the new stuff I've learned.

*/


int main(void)
{

    // Question 1 Testing:
    // Change these to test functionality
    TodoTask task1("task1", 4, 21), task2("task2", 4, 21);

    std::cout << (task1 == task2) << "\n" // Testing for isOnTheSameDate() functionality using == operator
              << (task1 > task2) << "\n" // Testing for isLater() functionality using > operator
              << task1.toString() << "\n" // Testing for toString()
              << task2.toString() << std::endl;


    // Question 2 Testing:
    // Change these to test functionality
    SimplePay pay1(60, 20.00), pay2(40, 20.00);

    std::cout << pay1.payMore(pay2) << "\n" // Testing for payMore()
              << pay1.toString() << "\n" // Testing for toString()
              << pay2.toString() << "\n" << std::endl;

}