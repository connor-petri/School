/* 

[X] steps [inside/outside] the [XX] yard line [Side 1/2]
[X] steps in [front/behind] the [front/back] [sideline/hash] 

range of x values is 0 - 160
range of y values is 0 - 84

*/
#include "dots.h"
#include <string>
#include <vector>


Dot::Dot(float newXSteps, char newInOut, int newYardLine, int newSide, float newYSteps, char newFrontBehind, char newFrontBack, char newSidelineOrHash)
{

    xSteps = newXSteps;
    inOut = newInOut;
    yardLine = newYardLine;
    side = newSide;

    ySteps = newYSteps;
    frontBehind = newFrontBehind;
    frontBack = newFrontBack;
    sidelineOrHash = newSidelineOrHash;

}


int Dot::getSide(void)
{
    return side;
}


std::vector<float> toCoordinates(void)
{

    std::vector<float> returnVector;
    float x;
    float y;

    
    returnVector.at(0) = x;
    returnVector.at(1) = y;
    return returnVector;

}