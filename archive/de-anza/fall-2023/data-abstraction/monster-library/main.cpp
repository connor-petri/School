#include <iostream>
#include "Monster/Monster.h"
#include "Monster/MonsterBuilder.h"
#include <fstream>
#include <json/json.h>

int main()
{

    Monster::tests();
    MonsterBuilder::tests();

    std::ifstream ifs("monster-lib.json", std::ifstream::binary);
    Json::Reader reader;
    Json::Value root;
    ifs >> root;

    return 0;
}
