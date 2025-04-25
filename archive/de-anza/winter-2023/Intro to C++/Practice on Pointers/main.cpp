#include <iostream>



bool isFirstThreeSameAsLastThree(int *arrptr, int size, bool &isSpecial)
{

    if (size < 3)
    {
        isSpecial = false;
        return false;
    }
    
    // isSame is true if the first three is equal to the last three. Otherwise it's false.
    bool isSame = true;
    isSpecial = false;

    if (*arrptr != *(arrptr + (size - 3)))
        isSame = false;

    if (*(arrptr + 1) != *(arrptr + (size - 2)))
        isSame = false;

    if (*(arrptr + 2) != *(arrptr + (size - 1)))
        isSame = false;

    // isSpecial is true if the first three are incrementing by one or if all 3 of the first nums are the same.
    if (*(arrptr + 1) == *arrptr + 1 && *(arrptr + 2) == *arrptr + 2)
        isSpecial = true;

    if (*(arrptr + 1) == *arrptr && *(arrptr + 2) == *arrptr)
        isSpecial = true;

    return isSame;

}


int toggleBlanks(char *strptr, int size, bool toggle, bool &allToggle)
{

    int numToggled = 0;
    allToggle = true;

    for (int i = 0; i < size; i++)
    {

        char *currptr = strptr + i;

        if (*currptr != ' ' && *currptr != '?')
        {
            allToggle = false;
            continue;
        }


        if (toggle && *currptr == ' ')
        {
            *currptr = '?';
            numToggled++;
        }
        else if (!toggle && *currptr == '?')
        {
            *currptr = ' ';
            numToggled++;
        }

    }

    return numToggled;

}


int main (void)
{

    // Change these to test question 1. Dont forget to change the size if you make the array larger or smaller:
    int arr[6] = { 1, 1, 1, 1, 1, 1 };
    int size = 6;
    int *arrptr = arr;
    bool isSpecial = false;

    std::cout << isFirstThreeSameAsLastThree(arrptr, size, isSpecial) << " " << isSpecial << std::endl;


    // Change these to test question 1. Dont forget to change the size if you make the array larger or smaller:
    char cstring[] = "? ? ";
    char *strptr = cstring;
    size = 5;
    bool toggle = true; // dont forget to change the toggle when testing.
    bool allToggle = true;

    std::cout << toggleBlanks(strptr, size, toggle, allToggle) << " " << allToggle << " " << cstring << std::endl;

}