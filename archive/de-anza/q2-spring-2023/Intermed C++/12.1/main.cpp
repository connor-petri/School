#include <iostream>
#include <string>
#include <fstream>

int main()
{
    // CHANGE THIS TO THE PATH THAT YOU HAVE THE INPUT AND OUTPUT FILE IN!!!
    const std::string PATH = "/Users/connor/School/Spring 2023/Intermed C++/12.1/";

    std::string sourceName, targetName;
    std::ifstream sStream;
    std::ofstream tStream;

    std::cout << "Enter a source file name: ";
    std::getline(std::cin, sourceName);
    std::cout << "Enter a target file name: ";
    std::getline(std::cin, targetName);

    sStream.open(PATH + "/" + sourceName);
    tStream.open(PATH + "/" + targetName);

    if (!sStream.is_open() || !tStream.is_open())
    {
        std::cout << "Error opening files.";
        return 1;
    }


    for (std::string line; std::getline(sStream, line); )
    {
        tStream << line << "\n";
    }

    sStream.close();
    tStream.close();

    std::cout << "Copy Done";

    return 0;

}