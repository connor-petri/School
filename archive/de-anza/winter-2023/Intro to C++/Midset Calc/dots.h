#pragma once

#include <tuple>


class Dot {

private:
    // Side to side dot data
    float xSteps;
    char inOut; // i or o
    int yardLine;
    int side; // 1 or 2

    // forward/backward dot data
    float ySteps;
    char frontBehind; // f or b
    char frontBack; // f or b
    char sidelineOrHash; // "front sideline" "front hash" "back hash" "back sideline"

    // coordinates converted from dot notation to a coordinate with (0,0) on the 0 yrd line side 1: on front sideline 
    float x;
    float y;

public:
    Dot(float newXSteps, char newInOut, int newYardLine, int newSide, float newYSteps, char newFrontBehind, char newFrontBack, char newSidelineOrHash);

    int getSide(void);
    void setSide(int newSide);

    std::vector<float> toCoordinates(void);

};

