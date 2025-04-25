#include <iostream>
#include <vector>
#include <string.h>

using namespace std;

int findLargestInfo(vector<int> vector1, bool &is_first_or_last, int &frequency) {

    int largest;
    int first_num;
    int last_num;

    frequency = 0;
    is_first_or_last = false;

    for (int i = 0; i < vector1.size(); i++) {

        if (i == 0) {

            first_num = vector1.at(i);
            largest = vector1.at(i);
            frequency++;

            continue;

        }

        if (vector1.at(i) > largest) {

            largest = vector1.at(i);
            frequency = 1;

        }
        else if (vector1.at(i) == largest) {

            frequency++;

        }

    }

    last_num = vector1.at(vector1.size() - 1);

    if ((first_num == largest) || (last_num == largest)) {

        is_first_or_last = true;

    }

    return largest;

}


bool isVarNameAccepted(char cstring[], int &num_upper, int &num_lower) {

    bool is_valid;
    
    bool is_lower;
    bool is_upper;

    // If the first letter of the cstring is a letter
    if (((cstring[0] >= 'a') && (cstring[0] <= 'z')) || ((cstring[0] >= 'A') && (cstring[0] <= 'Z'))) { 

        is_valid = true;

    }
    else {
        
        is_valid = false;
    
    }

    for (int i=0; i < strlen(cstring); i++) {

        is_lower = ((cstring[i] >= 'a') && (cstring[i] <= 'z'));
        is_upper = ((cstring[i] >= 'A') && (cstring[i] <= 'Z'));

        // If the current char is alphanumeric
        if ((is_lower) || (is_upper) || ((cstring[i] >= '0') && (cstring[i] <= '9'))) {

            if (is_lower) {num_lower++;}
            if (is_upper) {num_upper++;}

        }
        else {

            is_valid = false;

        }

    }

    return is_valid;

}


int main(void) {

    // Testing for question 1.

    bool is_first_or_last;
    int frequency;

    vector<int> my_vector(5);
    //Change these assignments to test the code
    my_vector.at(0) = 10;
    my_vector.at(1) = 20;
    my_vector.at(2) = 20;
    my_vector.at(3) = 10;
    my_vector.at(4) = 20;

    int largest = findLargestInfo(my_vector, is_first_or_last, frequency);

    cout << is_first_or_last << " " << frequency << " " << largest << endl;

    // Testing for question 2.
    char test_string[7] = "H123FF"; // Change this string to test the code
    int num_upper = 0;
    int num_lower = 0;

    cout << isVarNameAccepted(test_string, num_upper, num_lower) << " " << num_upper << " " << num_lower << endl;

    return 0;

}