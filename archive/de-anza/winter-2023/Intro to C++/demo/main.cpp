#include <iostream>

int main()
{


    int total = 0;

    for (int n = 1; n <= 5; n++)
    {
        total += (2*n) - 1;
    }

    std::cout << total;

}

