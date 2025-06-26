// Connor Petri

#include <iostream>
#include <vector>

int main()
{

    std::vector<float> weights(5);
    for (int i = 0; i < 5; i++)
    {
        std::cout << "Enter weight " << i + 1 << ": ";
        std::cin >> weights.at(i);
    }

    float totalWeight = 0.0f;
    float maxWeight = 0.0f;

    std::cout << "\nYou entered: ";

    for (float i : weights)
    {
        std::cout << i << " ";
        totalWeight += i;
        if (i > maxWeight)
            maxWeight = i;
    }

    std::cout << "\n\nTotal weight: " << totalWeight << "\n";
    std::cout << "Average weight: " << totalWeight / float(weights.size()) << "\n";
    std::cout << "Max Weight: " << maxWeight << "\n";

    return 0;

}
