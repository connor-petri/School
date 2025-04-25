#include <iostream>
#include <cctype>

bool isValid(const char cstring[11])
{

    int alpha = 0;
    int nums = 0;

    for (int i=0; i < strlen(cstring); i++)
    {

        if (std::isalpha(cstring[i]) && std::islower(cstring[i]))
            alpha++;

        if (std::isdigit(cstring[i]))
            nums++;

    }

    return (alpha == 4) && (nums == 6);

}


int main()
{

    char cstring[11];

    std::cout << "Enter a password consisting of exactly 4 lower case letters and 6 digits:\n";
    std::cin.getline(cstring, 11);

    bool valid = isValid(cstring);

    while (!valid)
    {

        std::cout << "Invalid password. Please enter a password with exactly 4 lower case letters and 6 digits\n"
                     "For example, 1234abcd56 is valid. Please enter again:\n";
        std::cin.getline(cstring, 11);

        valid = isValid(cstring);

    }

    std::cout << "The number of lower case letters in the password is 4\n"
                 "The number of digits in the password is 6";

    return 0;

}
