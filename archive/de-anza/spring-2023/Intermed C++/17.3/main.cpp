#include <iostream>
#include <map>
#include <vector>


int main()
{

    std::map<std::string, int> rooms;
    std::map<std::string, std::string> instructors, times;

    std::string courses[5] = { "CS101", "CS102", "CS103", "NT110", "CM241" };
    int rms[5] = { 102, 103, 203, 204, 304 };
    std::string names[5] = { "West", "Lee", "Kim", "Payne", "Johnson" };
    std::string tms[5] = { "8:00a", "9:00a", "10:00a", "11:00a", "1:00p" };

    for (int i = 0; i < 5; i++)
    {
        rooms[courses[i]] = rms[i];
        instructors[courses[i]] = names[i];
        times[courses[i]] = tms[i];
    }

    std::string userInput;

    std::cout << "Enter a course number to get information about that course: ";
    std::getline(std::cin, userInput);

    if (rooms[userInput] == 0)
    {
        std::cout << "Invalid course\n";
        return 1;
    }

    std::cout   << "Room number: " << rooms[userInput]
                << "\nInstructor: " << instructors[userInput]
                << "\nMeeting time: " << times[userInput] << std::endl;

    return 0;
}
