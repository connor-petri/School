//
// Created by Connor Petri on 3/18/23.
//
#include "dice.h"
#include <string>
#include <random>
#include <sstream>
#include <vector>

using namespace dice;


int dice::roll(int numSides)
{
    static std::random_device rd;
    static std::mt19937 gen(rd());
    static std::uniform_int_distribution<> dis(1, numSides);
    return dis(gen);
}

int dice::roll(const std::string& rollString)
{

    std::istringstream stream(rollString);
    std::string buffer;
    int numDice;
    int diceSides;
    int total = 0;

    try
    {

        if (rollString.find('d') == std::string::npos)
            throw std::exception();

        std::getline(stream, buffer, 'd');
        numDice = std::stoi(buffer);
        std::getline(stream, buffer);
        diceSides = std::stoi(buffer);
    }
    catch (std::exception)
    {
        throw std::runtime_error("DiceException: Invalid dice string. Please use format <int>d<int>.");
    }

    for (int i = 0; i < numDice; i++)
    {
        total += dice::roll(diceSides);
    }

    return total;

}
