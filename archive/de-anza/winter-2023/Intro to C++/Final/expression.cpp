//
// Created by Connor Petri on 3/28/23.
//

#include "expression.h"

using namespace final;

Expression::Expression(int operand1, char oper, int operand2)
: _op1(operand1), _oper(oper), _op2(operand2)
{
    if (oper != '+' && oper != '-')
        throw std::runtime_error("Invalid Operator Please use + or - only.");
}


int Expression::op1() const
{ return this->_op1; }

int Expression::op2() const
{ return this->_op2; }

char Expression::oper() const
{ return this->_oper; }


int Expression::toVal() const
{
    int total;
    if (this->_oper == '+')
        total = this->_op1 + this->_op2;
    else
        total = this->_op1 - this->_op2;

    return total;
}


std::string Expression::toString() const
{
    return "EXP(" + std::to_string(this->_op1) + " " + this->_oper + " " + std::to_string(this->_op2) + ")";
}

int Expression::compare(const Expression* ex) const
{

    int total1 = this->toVal();
    int total2 = ex->toVal();
    int output;

    if (total1 < total2)
        output = -1;
    if (total1 == total2)
        output = 0;
    if (total1 > total2)
        output = 1;

    return output;

}


bool Expression::hasOneOperandTheSame(const Expression* ex) const
{
    return (this->_op1 == ex->_op1 || this->_op1 == ex->_op2 || this->_op2 == ex->_op1 || this->_op2 == ex->_op2);
}


Expression* Expression::createDouble() const
{
    return new Expression(this->_op1 * 2, this->_oper, this->_op2 * 2);
}


int final::getLargest(Expression *arr, int size, int &numSame)
{

    int index;
    numSame = 0;

    for (int i = 0; i < size; i++)
    {

        if (i == 0)
        {
            index = 0;
            continue;
        }

        if (arr[i].toVal() == arr[index].toVal())
            numSame++;

        if (arr[i].toVal() > arr[index].toVal())
        {
            index = i;
            numSame = 0;
        }

    }

    return index;

}


int final::getExpInfo(Expression **arr, int size, Expression *search, int &numSame)
{
    int sameOper = 0;
    numSame = 0;

    for (int i = 0; i < size; i++)
    {

        if (arr[i]->hasOneOperandTheSame(search))
            sameOper++;

        if (arr[i]->compare(search) == 0)
            numSame++;

    }

    return sameOper;

}


std::vector<Expression*> final::getExpressionAndItsDouble(int op1, char oper, int op2)
{
    std::vector<Expression*> output;

    if (oper != '+' && oper != '-')
    {
        output.emplace_back(nullptr);
        output.emplace_back(nullptr);
    }
    else
    {
        output.emplace_back(new Expression(op1, oper, op2));
        output.emplace_back(output.at(0)->createDouble());
    }

    return output;
}


int final::getSlashReport(char *cstring, int size, bool &slashSlash, bool &onlySlashSlash)
{
    int numSlash = 0;
    slashSlash = (cstring[0] == '/' && cstring[1] == '/');
    onlySlashSlash = true;
    for (int i = 0; i < size; i++)
    {
        if (cstring[i] != '/')
            onlySlashSlash = false;
        else
            numSlash++;
    }

    return numSlash;

}










