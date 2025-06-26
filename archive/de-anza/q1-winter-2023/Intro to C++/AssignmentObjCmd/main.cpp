#include "assignment.h"

#include <string>
#include <vector>
#include <iostream>
#include <iomanip>

using namespace assignment;


int main(void)
{

    std::vector<Assignment> assignments;
    std::string userInput;

    std::cout << "Welcome to assignment recording program.\n";
    
    do
    {

        std::cout << "Please enter a command (add, list, stats or exit): ";
        std::cin >> userInput;

        if (userInput == "add")
        {
            addAssignment(assignments);
        }
        else if (userInput == "list")
        {
            listAssignments(assignments);
        }
        else if (userInput == "stats")
        {
            stats(assignments);
        }
        else if (userInput != "exit")
        {
            std::cout << "Unsupported command. Please try again.\n";
        }

    }
    while (userInput != "exit");

    std::cout << "\nThank you. Goodbye.";

}