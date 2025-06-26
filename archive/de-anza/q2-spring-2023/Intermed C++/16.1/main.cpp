#include <iostream>

class InvalidHr: public std::exception
{
public:
    std::string what() { return "The value of hr must be between 0 and 12."; }
};

class InvalidMin: public std::exception
{
public:
    std::string what() { return "The value of minutes must be between 0 and 59."; }
};

class InvalidSec: public std::exception
{
public:
    std::string what() { return "The value of seconds must be between 0 and 59."; }
};

int main()
{

    bool isValid = false;
    int hrs, min, sec;
    std::string ampm;

    do
    {
        try
        {
            std::cout << "Enter hours: ";
            std::cin >> hrs;
            if (hrs < 0 || hrs > 12) { throw InvalidHr(); }
            isValid = true;
            std::cout << "\n";
        }
        catch (InvalidHr& e)
        {
            std::cout << "\n" << e.what() << "\n";
        }
    } while (!isValid);

    isValid = false;

    do
    {
        try
        {
            std::cout << "Enter Minutes: ";
            std::cin >> min;
            if (min < 0 || min > 59) { throw InvalidMin(); }
            isValid = true;
            std::cout << "\n";
        }
        catch (InvalidMin& e)
        {
            std::cout << "\n" << e.what() << "\n";
        }
    } while (!isValid);

    isValid = false;

    do
    {
        try
        {
            std::cout << "Enter seconds: ";
            std::cin >> sec;
            if (sec < 0 || sec > 59) { throw InvalidSec(); }
            isValid = true;
            std::cout << "\n";
        }
        catch (InvalidSec& e)
        {
            std::cout << "\n" << e.what() << "\n";
        }
    } while (!isValid);

    do
    {
        std::cout << "Enter AM or PM: ";
        std::getline(std::cin, ampm);
    } while (ampm != "AM" && ampm != "PM");

    if (ampm == "PM") { hrs += 12; }

    std::cout << "24 hour clock time: " << hrs << ":" << min << ":" << sec << std::endl;

    return 0;

}
