#include <iostream>
#include <stdlib.h>

using namespace std;

void triangle()
{
    cout << "\nTriangle:\n\n";
    cout << "    *    \n";
    cout << "   ***   \n";
    cout << "  *****  \n";
    cout << " ******* \n";
    cout << "*********\n";
}

void square() 
{
    cout << "\nSquare:\n\n";
    cout << "##########\n";
    cout << "#        #\n";
    cout << "#        #\n";
    cout << "#        #\n";
    cout << "##########\n";
}

void diamond()
{
    cout << "\nDiamond:\n\n";
    cout << "\n    &    \n";
    cout << "   &  &  \n";
    cout << "  &    & \n";
    cout << "   &  &  \n";
    cout << "    &    \n";
}

int main()
{
    triangle();
    square();
    diamond();

    return 0;
}