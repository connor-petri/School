#include <iostream>
#include <iomanip>
#include <string>
#include <sstream>
#include <stdexcept>

using namespace std;


void new_assignment(ostringstream &OSS) { // creates a new assignment

    string user_input;
    string variable;
    string oper;
    string value;

    do {

        cout << "\nPlease enter an assignment:" << endl;

        cin >> variable >> oper >> value; // input should be <variable> = <value>

        if (cin.fail() || (oper != "=")) { // checks for valid input. puts an error message to cout if invalid input is given

            cout << "\nInvalid assignment. Please use format <variable> = <value>" << endl;

            if (cin.fail()) {

                cin.clear(); // clears the error state of cin
                cin.ignore(numeric_limits<streamsize>::max() + 3, '\n'); // ignore everything currently in the cin buffer

            }

        } 
        else {break;} // breaks when valid input is provided

    }
    while (true);

    cout << setw(7) << variable << setw(7) << oper << setw(7) << value << "\n" << endl; // Print the new assignment woth proper formatting

    OSS << setw(7) << variable << setw(7) << oper << setw(7) << value << endl; // Write the formatted assignment to the output string stream that was passed by reference.

}


string get_largest(ostringstream &OSS) { // Searches a passed in output string stream for the row with the highest value

    istringstream inSS(OSS.str()); // initializes inSS with the contents of the passed in OSS
    ostringstream formattingstream;

    string max_var;
    int max_val;

    string curr_var;
    string curr_oper;
    string curr_val;

    try {

        while (!inSS.eof()) { // While inSS is not empty

            inSS >> curr_var >> curr_oper >> curr_val; // get the values of an assignment from inSS;

            if (stoi(curr_val) > max_val) {

                max_var = curr_var;
                max_val = stoi(curr_val);

            }

        }

        formattingstream << setw(7) << max_var << setw(7) << "=" << setw(7) << max_val << endl; // formats the output using a ostringstream

        cout << "\nThe largest assignment:" << endl;
        return formattingstream.str(); // returns the contents of the formatting ostringstream

    }
    catch (invalid_argument) { // if there is an invalid_arguement exception thrown by stoi(), that means there is no assignments.

        return "\nThere is no assignment.\n";

    }

}


string get_all(ostringstream &OSS) { // returns all assignments

    if (OSS.str() == "") { // if OSS is empty return an error message

        return "\nThere is no assignment.\n";

    }
    else { // return the contents of OSS if OSS is not empty

        cout << "\nAll assignments:" << endl;
        return OSS.str();

    }

}


void menu(ostringstream &OSS) { // main menu loop

    int user_input;

    do {

        cout << "Assignment Menu:\n";
        cout << "   1. Enter a new assignment\n";
        cout << "   2. Show the largest assignment\n";
        cout << "   3. Show all assignments\n";
        cout << "   4. exit\n";
        cout << "Enter your option: ";

        cin >> user_input;


        if (cin.fail() || (user_input < 1) || (user_input > 4)) { // checks for invalid input.

            cout << "\nInvalid selection. Please try again.\n" << endl;

            if (cin.fail()) { // clears cin biffer and clears error state of cin

                cin.clear();
                cin.ignore(numeric_limits<streamsize>::max(), '\n');

            }

        }
        else { // If user_input is valid

            if (user_input == 4) {break;} // break if 4 is entered
            
            switch (user_input) { // function execution based on user input

            case 1:
                new_assignment(OSS); 
                break;

            case 2:
                cout << get_largest(OSS) << endl;
                break;

            case 3:
                cout << get_all(OSS) << endl;
                break;
            
            default:
                break;

            }
        
        }

    }
    while (true);

}


int main(void) {

    ostringstream OSS;

    cout << "\nWelcome to assignment recording program.\n\n";

    menu(OSS);

    cout << "\nThank you. Goodbye." << endl;

}