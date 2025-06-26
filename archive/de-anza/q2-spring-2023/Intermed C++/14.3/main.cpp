#include <iostream>

#include "Job.h"

int main()
{

    Job j1, j2;

    std::cin >> j1;
    std::cin >> j2;

    std::cout << "The difference between " << j1 << " and " << j2 << " is " << j1 - j2 << " hours.\n";
    std::cout << "The sum of " << j1 << " and " << j2 << " is " << j1 + j2 << " hours.\n";

    return 0;

}