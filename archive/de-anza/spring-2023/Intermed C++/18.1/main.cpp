#include <iostream>
#include <random>
#include <ctime>
#include <list>
#include <iomanip>

int randInt()
{
    static std::default_random_engine rng((unsigned) time(nullptr)); // This is static to ensure it stays the same between calls.
    std::uniform_int_distribution<int> dist(0, 100);
    return dist(rng);
}


int main()
{

    std::list<int> list;
    int sum = 0;
    float avg = 0.0f;

    for (int i = 0; i < 100; i++)
    {
        list.push_back(randInt());
    }

    for (auto i : list)
    {
        sum += i;
        std::cout << i << " ";
    }

    avg = (float)sum / 100.0f;

    std::cout   << "\nThe sum of the elements is: " << sum
                << "\nThe average of the elements is: " << std::fixed << std::setprecision(2) << avg << std::endl;

    return 0;

}
