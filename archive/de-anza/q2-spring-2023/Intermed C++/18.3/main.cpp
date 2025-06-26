#include <iostream>
#include <queue>
#include <fstream>

#define FILENAME1 "file1.txt"
#define FILENAME2 "file2.txt"

std::queue<char> loadFile(const std::string& fileName)
{
    std::ifstream file(fileName);
    std::queue<char> output;
    char c;

    if (!file.is_open())
    {
        throw std::runtime_error((std::string)"Error opening " + FILENAME1);
    }

    while (file >> c)
    {
        output.push(c);
    }

    file.close();

    return output;
}


int main()
{
    std::queue<char> fq1, fq2;
    fq1 = loadFile(FILENAME1);
    fq2 = loadFile(FILENAME2);

    bool isSame = true;

    while (isSame && !fq1.empty() && !fq2.empty())
    {
        if (fq1.front() != fq2.front())
        {
            isSame = false;
        }

        fq1.pop(); fq2.pop();
    }

    if (isSame)
    {
        std::cout << "The files are identical." << std::endl;
    }
    else
    {
        std::cout << "The files are not identical." << std::endl;
    }

    return 0;
}
