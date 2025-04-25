#include <iostream>
#include <iomanip>

using namespace std;

int countAgainstFirstNumber(bool &allGreaterOrLessThan) {

    int first_num;
    int current_num;
    int ticker = 0;

    bool has_greater_than = false;
    bool has_less_than = false;

    cin >> first_num;

    while (true) {

        cin >> current_num;

        if (current_num != first_num) {

            ticker++;

            if (current_num < first_num) {has_less_than = true;}

            if (current_num > first_num) {has_greater_than = true;}

        } else {

            break;

        }

    }

    if (((has_greater_than) && (!has_less_than)) || ((!has_greater_than) && (has_less_than))) {

        allGreaterOrLessThan = true;

    } else {

        allGreaterOrLessThan = false;

    }
    
    return ticker;

}

void countGradeList(int &num_passing, int &num_a, int &num_invalid) {

    int count;
    char grade;

    cout << "Enter number of grades: ";
    cin >> count;

    for (int i=0; i < count; i++) {

        cout << "(Capital Letter Only) Enter grade " << (i + 1) << ": ";
        cin >> grade;

        if (grade == 'A') {

            num_a++;
            num_passing++;

        } else if ((grade == 'B') || (grade == 'C')) {

            num_passing++;

        } else if ((grade != 'D') && (grade != 'F')) {

            num_invalid++;

        }

    }

}


// Testing Functions in main()
int main(void) {

    // Question 1 Tests

    int num_between;
    bool true_or_false;

    num_between = countAgainstFirstNumber(true_or_false);

    cout << num_between << " " << true_or_false << endl; // bools is printed as 0 for false and 1 for true.

    // Question 2 Testing

    int num_passing = 0;
    int num_a = 0;
    int num_invalid = 0;

    countGradeList(num_passing, num_a, num_invalid);

    cout << num_passing << " " << num_a << " " << num_invalid << endl;

    return 0;

}