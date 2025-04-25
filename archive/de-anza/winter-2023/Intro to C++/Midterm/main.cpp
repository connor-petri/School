#include <iostream>


bool hasDashDash(char cstring[], int size, bool &isLeadingOrTrailing)
{

    char prevChar;
    bool dashDash = false;
    isLeadingOrTrailing = false;

    if ((cstring[0] == '-' && cstring[1] == '-') || (cstring[size-1] == '-' && cstring[size-2] == '-'))
    {

        isLeadingOrTrailing = true;
        dashDash = true;

        return dashDash;

    }

    for (int i=0; i < size; i++)
    {

        if (cstring[i] == '-' && prevChar == '-')
        {
            dashDash = true;
        }

        prevChar = cstring[i];

    }

    return dashDash;

}


int checkBlanks(char cstring[], int size, bool &allBlanks, int &leadingBlanks)
{

    int numBlanks = 0;
    allBlanks = true;
    leadingBlanks = 0;

    for (int i=0; i < size; i++)
    {

        if (cstring[i] == ' ')
        {
            
            if (allBlanks)
            {
                leadingBlanks++;
            }

            numBlanks++;

        }
        else
        {
            allBlanks = false;
        }

    }

    return numBlanks;

}


int removeNegatives(int arr[], int size, bool &allNegOne, bool &allZeros)
{

    int numNeg = 0;
    allNegOne = true;
    allZeros = true;

    for (int i=0; i < size; i++)
    {
        if (arr[i] != -1)
        {
            allNegOne = false;
        }

        if (arr[i] < 0)
        {
            
            numNeg++;
            arr[i] = 0;

        }

        if (arr[i] != 0)
        {
            allZeros = false;
        }

    }

    return numNeg;

}


int readUntilZeroZero(void)
{

    int count = 0;
    int prevNum;
    int userInput;

    std::cout << "Please enter the list ending with zero and zero:" << std::endl;

    do
    {

        std::cin >> userInput;

        if (count == 0)
        {
            prevNum = userInput;
            count++;
            continue;
        }

        if (userInput == 0 && prevNum == 0)
        {
            break;
        }

        prevNum = userInput;
        count++;
        
    } while (true);

    return count - 1;

}


int main(void)
{

    // Testing for Q1:
    bool leadingOrTrailing;
    char testString[6] = "dd--c"; // Change this string to test the function. Make sure to pass in the string's size to the function if you change the size of the cstring.

    std::cout << hasDashDash(testString, 6, leadingOrTrailing) << " " << leadingOrTrailing << std::endl;


    // Testing for Q2:
    bool allBlanks;
    int leadingBlanks;
    char testString2[6] = "  f  "; // Change this string to test the function. Make sure to pass in the string's size to the function if you change the size of the cstring.

    std::cout << checkBlanks(testString2, 6, allBlanks, leadingBlanks) << " " << allBlanks << " " << leadingBlanks << std::endl;


    // Testing for Q3:
    int testArr[5] = { -1, -1, -1, -1, -1 }; // Change this string to test the function. Make sure to pass in the string's size to the function if you change the size of the cstring.
    bool allNeg1;
    bool allZeros;

    std::cout << removeNegatives(testArr, 5, allNeg1, allZeros) << " " << allNeg1 << " " << allZeros << std::endl;


    // Testing for Q4:
    int count = readUntilZeroZero();
    
    std::cout << "Count: " << count << std::endl;

    return 0;

}