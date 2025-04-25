#include <iostream>
#include <fstream>

using std::string, std::ifstream, std::cin, std::cout, std::getline;

int main()
{
    // Change this to the path containing your .txt file.
    const string PATH = "/Users/connor/School/Spring 2023/Intermed C++/12.3/";
    string fileName;
    ifstream file;
    int numRead = 0;

    cout << "Enter the file name: ";
    getline(cin, fileName);

    file.open(PATH + fileName);

    if (!file.is_open())
    {
        cout << "File not found. Exiting...";
        return -1;
    }

    file.seekg(-89, file.end);

    for (string line; getline(file, line); )
    {
        cout << line << "\n";
        numRead++;
    }

    if (numRead < 10)
        cout << "Entire file has been displayed\n";

    return 0;

}
