#include <iostream>
#include <string>


std::string addSpaces(std::string input)
{

    for (int i=0; i < input.size(); i++)
    {
        if (isupper(input[i]) && i != 0)
        {
            input.insert(i, " ");
            i++;
        }
    }

    return input;

}


int main()
{

    std::string userInput;

    std::cout << "Enter a sentence with no spaces between the words\n"
                 "and the first letter of each word capitalized.\n"
                 "(Example: StopAndSmellTheRoses): ";

    std::cin >> userInput;

    std::cout << "output => " << addSpaces(userInput) << "\n";

}
