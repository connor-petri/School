#include <iostream>


int main()
{

    int numScores;
    bool isValid;
    float average = 0.0f;

    std::cout << "How many test scores will you enter? ";
    std::cin >> numScores;

    auto *grades = new float[numScores];

    for (int i = 0; i < numScores; i++)
    {

        isValid = false;

        do
        {

            std::cout << "Enter test score " << i+1 << ": ";
            std::cin >> *(grades + i);

            if (!std::cin.fail() && *(grades + i) >= 0)
            {
                isValid = true;
            }
            else
            {
                std::cout << "Negative scores are not allowed.\n";
            }

        } while (!isValid);

        average += *(grades + i);

    }

    average /= (float)numScores;

    // Selection Sort
    int minIndex;

    for (int i = 0; i < numScores-1; i++)
    {

        minIndex = i;
        for (int j = i+1; j < numScores; j++)
        {

            if (*(grades + j) < *(grades + i))
                minIndex = j;

            if (minIndex != i)
            {
                float temp = *(grades + i);
                *(grades + i) = *(grades + j);
                *(grades + j) = temp;
            }

        }

    }

    std::cout << "\nThe test scores in ascending order, and their average, are:\n\nScore\n----\n\n";

    for (int i = 0; i < numScores; i++)
    {
        std::cout << *(grades + i) << "\n";
    }

    std::cout << "\nAverage score: " << average;

}