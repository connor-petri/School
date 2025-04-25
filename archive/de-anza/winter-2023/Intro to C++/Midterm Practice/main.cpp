#include <iostream>
#include <string.h>

using namespace std;


bool isInsideQuotes(char cstring[]) {

    int endIndex = strlen(cstring) - 1;
    bool isValid = true;

    if ((cstring[0] != '\'') || (cstring[endIndex] != '\'')) {

        return false;

    }

    for (int i = 0; i < strlen(cstring); i++) {

        if ((i == 0) || (i == endIndex)) {

            continue;

        }

        if (cstring[i] == '\'') {

            isValid = false;

        }

    }

    return isValid;

}


bool isJustZeroAndOne(char cstring[], bool &isJustOnes, bool &isJustZeros) {

    bool allZerosAndOnes = true;

    for (int i=0; i < strlen(cstring); i++) {

        if ((cstring[i] != '0') && (cstring[i] != '1')) {

            allZerosAndOnes = false;
            isJustZeros = false;
            isJustOnes = false;
            
            break;

        }

        if (cstring[i] != '0') {

            isJustZeros = false;

        }

        if (cstring[i] != '1') {

            isJustOnes = false;

        }

    }

    return allZerosAndOnes;

}


bool isIncrementingBy1(int arr[], int arrSize, bool& allGreaterThan0) {

    int prevNum = arr[0];
    bool isIncrementing = true;

    allGreaterThan0 = true;

    for (int i = 0; i < arrSize; i++) {

        if (arr[i] <= 0) {
            allGreaterThan0 = false;
        }

        if (i == 0) {
            continue;
        }

        if (arr[i] != prevNum + 1) {
            isIncrementing = false;
        }

        prevNum = arr[i];

    }

    return isIncrementing;

}


int readNumUntilRepeated(int& firstNum, int& lastNum) {

    int count = 0;
    int userInput;

    cout << "Please enter numbers until the first or last number is repeated:" << endl;

    do {

        cin >> userInput;

        if (count == 0) {

            firstNum = userInput;
            lastNum = userInput;
            count++;
            continue;

        }

        if (userInput == firstNum || userInput == lastNum) {
            break;
        }

        lastNum = userInput;
        count++;

    }
    while (true);


    return count;

}


int main(void) {

    // Testing for question 1
    char testString[8] = "'Hello'"; // change this string to test the function

    cout << isInsideQuotes(testString) << endl;


    // Testing for question 2
    char testString2[6] = "55555"; // change this string to test the function.
    bool isAllOnes = true;
    bool isAllZeros = true;

    cout << isJustZeroAndOne(testString2, isAllOnes, isAllZeros) << " " << isAllOnes << " " << isAllZeros << endl;


    // Testing for question 3
    int testArr[5] = { 0, 1, 2, 3, 4 }; // Change this array to test the function.
    bool allGreaterThan0;

    cout << isIncrementingBy1(testArr, 5, allGreaterThan0) << " " << allGreaterThan0 << endl;


    // Testing for question 4
    int firstNum, lastNum;

    int count = readNumUntilRepeated(firstNum, lastNum);

    cout << "Count: " << count << endl;
    cout << "First Num: " << firstNum << endl;
    cout << "Last Num: " << lastNum << endl;



    return 0;

}