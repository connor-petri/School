#include <iostream>
#include <iomanip>

using namespace std;


int main() {

    int num1;
    int num2;
    char AorB;
    bool error = false;

    cout << "Please enter the first operand:  ";
    cin >> num1;

    if (cin.fail()) {error = true; cout << "Unable to read the number for the first operand." << endl;}

    if (!error) {
        cout << "\nPlease enter the second operand: ";
        cin >> num2;

        if (cin.fail()) {error = true; cout << "Unable to read the number for the second operand." << endl;}
        
    }

    if (!error) {

        cout << "\nPlease enter the printing option ('A' or 'B'): ";
        cin >> AorB;

        if ((cin.fail()) || ((AorB != 'A') && (AorB != 'B'))) {

            cout << AorB << endl;
    
            error = true; 
        
            cout << "Unsupported printing option." << endl;
    
        }

    }


    if (!error) {

        cout << num1 << setw(5) << "+" << setw(5) << num2 << setw(5) << "=" << setw(5) << (num1 + num2) << endl;

        cout << num1 << setw(5) << "-" << setw(5) << num2 << setw(5) << "=" << setw(5) << (num1 - num2) << endl;

        if (AorB == 'A') {

            cout << num1 << setw(5) << "*" << setw(5) << num2 << setw(5) << "=" << setw(5) << (num1 * num2) << endl;

            cout << num1 << setw(5) << "/" << setw(5) << num2 << setw(5) << "=" << setw(5) << (num1 / num2) << endl;

            cout << num1 << setw(5) << "%" << setw(5) << num2 << setw(5) << "=" << setw(5) << (num1 % num2) << endl;

        }

    }


    return 0;

}