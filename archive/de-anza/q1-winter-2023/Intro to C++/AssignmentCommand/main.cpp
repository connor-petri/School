// Connor Petri
#include <iostream>
#include <iomanip>
#include <vector>


struct assignment // This struct holds the variable name as assignment.key and the value of the assignment as assignment.value
{
    std::string key;
    int value;
};


void addAssignment(std::vector<assignment> &assignments) // Gets user input, checks for errors, and then puts userInput into an assignment and puts it into the assignments vector
{

    std::string variable;
    char oper;
    int value;

    do 
    {

        std::cout << "\nPlease enter an assignment:" << std::endl;

        std::cin >> variable >> oper >> value; // input should be <variable> = <value>

        if (std::cin.fail() || (oper != '=')) // checks for valid input. puts an error message to cout if invalid input is given
        { 

            std::cout << "\nInvalid assignment. Please use format <variable> = <value>" << std::endl;

            if (std::cin.fail()) 
            {

                std::cin.clear(); // clears the error state of cin
                std::cin.ignore(std::numeric_limits<std::streamsize>::max() + 3, '\n'); // ignore everything currently in the cin buffer

            }

        } 
        else {break;} // breaks when valid input is provided

    }
    while (true);

    std::cout << std::setw(7) << variable << std::setw(7) << "=" << value << std::endl; // print the assignment

    assignment NewAssignment; // Initialize a new assignment

    NewAssignment.key = variable; // Pack variables into assignment
    NewAssignment.value = value;

    assignments.push_back(NewAssignment); // Add the assignment to the assignments vector

}


void listAssignments(std::vector<assignment> &assignments) // Iterates through all assignments and prints them with formatting
{

    if (assignments.size() == 0) // If assignments vector is empty then print an error message and exit the function.
    {
        std::cout << "There is no assignment." << std::endl;
        return;
    }

    for (int i=0; i < assignments.size(); i++) // iterates through the assignments vector and printes each one with formatting.
    {
            
        std::cout << std::setw(7) << assignments.at(i).key << std::setw(7) << "=" << std::setw(7) << assignments.at(i).value << std::endl;

    }
    

}


void stats(std::vector<assignment> &assignments) // Prints stats about the list of assignments
{

    int totalAssign = 0;
    int totalPos = 0;
    int totalNeg = 0;

    assignment largest;
    assignment smallest;

    if (assignments.size() == 0)
    {
        std::cout << "There is no assignment." << std::endl;
        return;
    }

    largest = assignments.at(0);
    smallest = assignments.at(0);

    for (int i=0; i < assignments.size(); i++)
    {
            
        totalAssign++;

        if (assignments.at(i).value < 0)
        {
            totalNeg++;
        }

        if (assignments.at(i).value > 0)
        {
            totalPos++;
        }

        if (i == 0)
        {
            continue;
        }

        if (largest.value < assignments.at(i).value)
        {
            largest = assignments.at(i);
        }

        if (smallest.value > assignments.at(i).value)
        {
            smallest = assignments.at(i);
        }

    }

    std::cout << std::setw(10) << "Total number of assignments:" << std::setw(10) << totalAssign << std::endl;
    std::cout << std::setw(10) << "Total number of positive assignments:" << std::setw(10) << totalPos << std::endl;
    std::cout << std::setw(10) << "Total number of negative assignments:" << std::setw(10) << totalNeg << std::endl;
    std::cout << std::setw(10) << "The largest assignment:" << std::setw(7) << largest.key << std::setw(7) << "=" << std::setw(7) << largest.value << std::endl;
    std::cout << std::setw(10) << "The smallest assignment:" << std::setw(7) << largest.key << std::setw(7) << "=" << std::setw(7) << largest.value << std::endl;

}


void menu(std::vector<assignment> &assignments)
{

    std::string userInput;

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
            std::cout << "Unsupported command. Please try again." << std::endl;
        }

    } 
    while (userInput != "exit");
    
}


int main(void)
{
    std::vector<assignment> assignments;

    std::cout << "Welcome to assignment recording program." << std::endl;

    menu(assignments);
    
    std::cout << "\nThank you. Goodbye." << std::endl;

    return 0;

}