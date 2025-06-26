#include <iostream>
#include <iomanip>
#include <string>
#include <vector>
#include <type_traits>
#include <cmath>

#define NUMACCOUNTS 20

struct Account
{
    std::string name;

    std::string address;
    std::string city;
    std::string state;
    int zip;
    std::string phoneNum;

    float balance;
    std::string lastPayDate;
};

template <typename T>
T promptUser(const std::string& prompt) // using a template avoids writing 2 versions of the same function
{
    T output;
    bool isValid;

    do
    {
        isValid = true;
        std::cout << std::fixed;

        std::cout << prompt;

        if constexpr (std::is_same_v<T, std::string>)
        {
            std::getline(std::cin, output);
        }
        else
        {
            std::cin >> output;
            std::cin.ignore(INT32_MAX, '\n');
        }

        if (std::cin.fail())
        {
            isValid = false;
            std::cout << "Invalid value.\n";
            std::cin.clear();
            std::cin.ignore(INT32_MAX, '\n');
            continue; // continue is used to ensure if the cin buffer is in a fail state that we do not take the next if branch
        }

        if constexpr (std::is_same_v<T, float>)
        {
            if (output < 0.0f)
            {
                isValid = false;
                std::cout << "Balance cannot be negative.\n";
            }

            if (std::isnan(output))
                isValid = false;
        }

    } while (!isValid);

    return output;
}


Account newAccount()
{
    Account output =
            {
                promptUser<std::string>("Customer name: "),
                promptUser<std::string>("Customer address: "),
                promptUser<std::string>("City: "),
                promptUser<std::string>("State: "),
                promptUser<int>("Zip: "),
                promptUser<std::string>("Telephone: "),
                promptUser<float>("Account balance: $"),
                promptUser<std::string>("Date of last payment: ")
            };

    return output;
}

void printInfo(const Account& account)
{
    std::cout   << "Customer name: " << account.name << "\n"
                << "Customer address: " << account.address << "\n"
                << "City: " << account.city << "\n"
                << "State: " << account.state << "\n"
                << "Zip: " << account.zip << "\n"
                << "Telephone: " << account.phoneNum << "\n"
                << "Account balance: $" << std::fixed << std::setprecision(2) << account.balance << "\n"
                << "Date of last payment: " << account.lastPayDate << "\n";
}

int main()
{

    Account accounts[NUMACCOUNTS];
    std::string tempInput;
    int userInput;
    unsigned short nextIndex = 0;

    do
    {

        std::cout   << "1. Enter new account information\n"
                    << "2. Change account information\n"
                    << "3. Display all account information\n"
                    << "4. Exit the program\n\n"
                    << "Enter your choice: ";

        std::getline(std::cin, tempInput);
        userInput = std::stoi(tempInput);
        std::cout << std::endl;

        if (std::cin.fail() || (userInput < 1 || userInput > 4)) // Input validation
        {
            std::cout << "Invalid input. Valid inputs are 1, 2, 3, and 4\n\n";
            if (std::cin.fail())
            {
                std::cin.clear();
                std::cin.ignore(INT32_MAX, '\n');
            }
        }

        switch (userInput)
        {
            case 1:
                accounts[nextIndex] = newAccount();
                std::cout << "You have entered information for customer number " << nextIndex << "\n";
                nextIndex++;
                break;

            case 2:
                unsigned short index;
                std::cout << "Customer number: ";
                std::cin >> index;
                std::cin.ignore(INT32_MAX, '\n');
                printInfo(accounts[index]);
                accounts[index] = newAccount();
                break;

            case 3:
                if (nextIndex == 0)
                {
                    std::cout << "There are no accounts.\n\n";
                    break;
                }

                for (int i = 0; i < nextIndex; i++)
                {
                    printInfo(accounts[i]);
                }
                break;

            default:
                break;
        }

    } while (userInput != 4);

}
