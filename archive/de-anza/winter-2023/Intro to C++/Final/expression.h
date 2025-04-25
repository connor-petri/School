//
// Created by Connor Petri on 3/28/23.
//

#pragma once

#include <string>
#include <vector>

namespace final
{
    class Expression
    {
    private:
        int _op1;
        char _oper;
        int _op2;

    public:
        Expression(int operand1, char oper, int operand2);

        int op1() const;
        int op2() const;
        char oper() const;

        int toVal() const;

        std::string toString() const;

        int compare(const Expression* ex) const;

        bool hasOneOperandTheSame(const Expression* ex) const;

        Expression* createDouble() const;


    };

    int getLargest(Expression *arr, int size, int &numSame);
    std::vector<Expression*> getExpressionAndItsDouble(int op1, char oper, int op2);
    int getSlashReport(char *cstring, int size, bool &slashSlash, bool &onlySlashSlash);

    int getExpInfo(Expression **arr, int size, Expression* search, int &numSame);
}