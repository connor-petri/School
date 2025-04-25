#include <iostream>
#include <string>
#include <iomanip>

#define PRECISION 2

struct MovieData
{
    std::string title;
    std::string director;
    int year;
    int runtimeMin;
    float prodCost;
    float firstYearRev;
    float firstYearProfit;
};

void printData(const MovieData movie)
{
    std::cout << std::fixed << std::setprecision(PRECISION); // Stops cout from using scientific notation and rounds to 2 decimals

    std::cout   << "Title: " << movie.title
                << "\nDirector: " << movie.director
                << "\nReleased: " << movie.year
                << "\nRunning Time: " << movie.runtimeMin << " minutes"
                << "\nProduction Cost: $" << movie.prodCost
                << "\nFirst Year Revenue: $" << movie.firstYearRev;
    if (movie.firstYearProfit < 0)
        std::cout << "\nFirst Year Profit: $";
    else
        std::cout << "\nFirst Year Loss: $";

    std::cout << movie.firstYearProfit << std::endl;

}

int main()
{
    MovieData mov1 = { "War of the Worlds",
                       "Byron Haskin",
                       1953,
                       88,
                       15000000.00f,
                       28000000.00f,
                       13000000.00f };

    MovieData mov2 = { "War of the Worlds",
                       "Stephen Spielberg",
                       2005,
                       118,
                       22000000.00f,
                       14000000.00f,
                       -8000000.00f };

    printData(mov1);
    std::cout << "\n\n";
    printData(mov2);

    return 0;

}
