#include <iostream>
#include <iomanip>
#include "EmployeeInfo.h"

float promptUser(const std::string& message, const std::string& errorMessage)
{
    float output;

    std::cout << message;
    std::cin >> output;

    while (output < 0)
    {
        std::cout << errorMessage;
        std::cin >> output;
    }

    return output;
}

int main()
{
    std::string userInput;
    bool isFirstLoop = true;

    std::cout << "Welcome to the payroll program.\n";

    do
    {
        EmployeeInfo employee;

        std::cout << "Enter the employee name (stop to exit): ";

        if (isFirstLoop)
            isFirstLoop = false;
        else
            std::cin.ignore(INT32_MAX, '\n');

        std::getline(std::cin, userInput);

        if (userInput != "stop")
        {
            employee.name(userInput);
            employee.hourlyRate(promptUser("Please enter hourly rate: ",
                                           "Invalid hourly rate. Please enter positive numbers only: "));
            employee.numHours(promptUser("Please enter the number of hours worked: ",
                                         "Invalid amount of hours worked. Please enter positive numbers only: "));

            std::cout << "Employee Name: " << employee.name()
                      << "\nWeekly pay amount: $" << std::fixed << std::setprecision(2) << employee.calculatePay() << "\n\n";
        }

    } while (userInput != "stop");

    return 0;

}
