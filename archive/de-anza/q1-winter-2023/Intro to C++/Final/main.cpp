#include <iostream>
#include "expression.h"

using namespace final;

// Assumptions: Public getters are needed as additional public member functions in order to access
// private data members in the Expression class. I also added a toVal() public method because i got tired
// of writing if else statements to get the Expression's value.

int main()
{
    // Tests
    Expression testArr[3] = { Expression(1, '+', 1), Expression(1, '+', 2), Expression(1, '+', 3) };
    int numSame;
    int largest = getLargest(testArr, 3, numSame);
    std::cout << largest << " " << numSame << "\n";

    Expression *testArr2[3] = { new Expression(1, '+', 1), new Expression(1, '+', 2), new Expression(1, '+', 3) };
    int numSameOp = getExpInfo(testArr2, 3, new Expression(1, '+', 3), numSame);
    std::cout << numSameOp << " " << numSame << "\n";

    std::vector<Expression*> testVector = getExpressionAndItsDouble(3, '-', 1);
    std::cout << testVector.at(0)->toString() << " " << testVector.at(1)->toString() << "\n";

    char cstring[6] = "//Hi!";
    bool slashSlash = false;
    bool onlySlashSlash = false;
    int numSlashes = getSlashReport(cstring, 6, slashSlash, onlySlashSlash);
    std::cout << numSlashes << " " << slashSlash << " " << onlySlashSlash << "\n";

}