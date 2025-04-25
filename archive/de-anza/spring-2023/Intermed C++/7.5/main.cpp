#include <iostream>


// Connor Petri
// My partner didn't respond to either my zoom message or my canvas email, so I did this on my own.
// I would prefer to work alone.


int main()
{



    int shipTable[5][3] =
            {
            {1, 50 ,20},
            {51, 100, 15},
            {101, 200, 10},
            {201, 500, 5},
            {501, INT32_MAX, 0}
            };

    int numOrdered;
    bool isValid;

    do
    {
        isValid = true;
        std::cout << "Number ordered (negative number or 0 to end): ";
        std::cin >> numOrdered;

        if (std::cin.fail())
        {
            std::cin.clear();
            std::cin.ignore(INT32_MAX, '\n');
            std::cout << "Invalid input.\n\n";

            numOrdered = 1; // needed to keep while loop running in case of an error.
            isValid = false;
        }

        if (numOrdered <= 0)
            isValid = false;

        if (isValid)
        {
            for (int i = 0; i < 5; i++)
            {
                if (numOrdered >= shipTable[i][0] && numOrdered <= shipTable[i][1])
                {
                    std::cout << "Shipping charge for a quantity of " << numOrdered << " is $" << shipTable[i][2]
                              << "\n";
                }
            }

            std::cout << "\n";
        }

    } while (numOrdered > 0);

    return 0;

}
