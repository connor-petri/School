#include <iostream>
#include <string>
#include <iomanip>
#include <cctype>

using namespace std;

int main() {
    string itemName;
    double itemPrice;
    int quantity;
    char isGift;
    string occasion;

    double subTotal;
    double taxRate = 0.10;
    double tax;
    double total;

    // Gather item information.
    cout << "Please enter the name of the item: ";
    cin >> itemName;
    
    cout << "\n\nPlease enter the price for each item: ";
    cin >> itemPrice;

    cout << "\n\nPlease enter the quantity: ";
    cin >> quantity;

    cout << "\n\n Is this a gift? (y/n): ";
    cin >> isGift;

    cout << "\n\nPlease enter the description of the occasion: ";
    cin >> occasion;
    
    // Calculating subTotal, tax, and total
    subTotal = itemPrice * quantity;
    tax = subTotal * taxRate;
    total = subTotal + tax;

    // Output
    cout << "========== RECEIPT ==========\n";
    cout << "Name:        " << itemName << endl;
    cout << fixed << setprecision(2) << "Price:      $" << itemPrice << endl;
    cout << "Quantity:    " << quantity << endl;
    cout << fixed << setprecision(2) << "Subtotal:   $" << subTotal << setprecision(2) << endl;
    cout << fixed << setprecision(2) << "Tax:        $" << tax << setprecision(2) << endl;
    cout << fixed << setprecision(2) << "Total:      $" << total << setprecision(2) << endl;
    cout << "Gift? (y/n): " << isGift << endl;
    cout << "Occasion:    " << occasion << endl;
    cout << "============================" << endl;


    return 0;

}