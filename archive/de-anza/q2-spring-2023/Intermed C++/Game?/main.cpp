#include <iostream>
#include "entities/Entity.h"
#include <unistd.h>

using namespace game;

int main()
{
    entities::Entity e1("Entity 1", 200), e2("Entity 2", 200);

    do
    {

        int dmg = roll(20, 50);
        e2.takeDamage(dmg);
        std::cout << e1.name() << " (" << e1.hp() << '/' << e1.maxHp() << ") attacks, doing " << dmg << " damage.\n";

        sleep(2);

        dmg = roll(20, 50);
        e1.takeDamage(dmg);
        std::cout << e2.name() << " (" << e2.hp() << '/' << e2.maxHp() << ") attacks, doing " << dmg << " damage.\n";

        sleep(2);

    } while (e1.hp() > 0 && e2.hp() > 0);

    return 0;
}
