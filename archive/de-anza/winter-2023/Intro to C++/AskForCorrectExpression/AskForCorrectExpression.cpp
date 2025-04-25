#include <iostream>
#include <iomanip>
#include <string>

using namespace std;



void operand_check(int& operand, string first_or_second) {

    while (true) {

        cout << "Please enter the " << first_or_second << " operand:  "; // Prompts the user for input
        cin >> operand; // stores input to the pass by reference variable orerand

        if (cin.fail()) { // operand has to be an int

            cout << "Unable to read the number for the " << first_or_second << " operand. Please try again." << endl;

            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');

        } else if (operand < 0) { // operand can be negative

            cout << "The " << first_or_second << " operand cannot be negative. Please try again." << endl;

        } else { // Break if the input is an int greater than 0

            break;

        }

    }

}



int main() {

    int num1;
    int num2;
    int result;
    char operator1;
    bool valid_operator = false;

    operand_check(num1, "first"); // Gets user input and checks that it is a non-negative int
    operand_check(num2, "second");

    while (!valid_operator) { // checks that the operand is valid

        cout << "Please enter the expression operator: ";
        cin >> operator1;

        if (cin.fail()) {

            cout << "Unsupported operator. Please try again.";

            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');

        } else {

            switch (operator1)
            {
            case '+':
                result = num1 + num2;
                valid_operator = true;
                break;
            case '-':
                result = num1 - num2;
                valid_operator = true;
                break;
            case '*':
                result = num1 * num2;
                valid_operator = true;
                break;
            case '/':
                result = num1 / num2;
                valid_operator = true;
                break;
            case '%':
                result = num1 % num2;
                valid_operator = true;
                break;
            
            default:
                cout << "Unsupported operator. Please try again." << endl;
                break;
            }

        }

    }

    cout << num1 << setw(5) << operator1 << setw(5) << num2 << setw(5) << "=" << setw(5) << result << endl;

}

