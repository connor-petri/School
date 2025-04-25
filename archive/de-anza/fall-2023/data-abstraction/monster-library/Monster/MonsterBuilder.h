//
// Created by Connor Petri on 10/8/23.
//

#ifndef MONSTER_LIBRARY_MONSTERBUILDER_H
#define MONSTER_LIBRARY_MONSTERBUILDER_H

#include "Monster.h"

class MonsterBuilder
{
public:
    static MonsterBuilder* create(const std::string &name);

    MonsterBuilder* size(const std::string &size) { this->newMonster->setSize(size); return this; }
    MonsterBuilder* type(const std::string &type) { this->newMonster->setType(type); return this; }
    MonsterBuilder* alignment(const std::string &alignment) { this->newMonster->setAlignment(alignment); return this; }

    MonsterBuilder* ac(int ac) { this->newMonster->setAC(ac); return this; }
    MonsterBuilder* hp(int hp) { this->newMonster->setHP(hp); return this; }
    MonsterBuilder* hitDice(const std::string &hitDice) { this->newMonster->setHitDice(hitDice); return this; }
    MonsterBuilder* speed(const std::string &speed) { this->newMonster->setSpeed(speed); return this; }

    MonsterBuilder* str(unsigned int str) { this->newMonster->setStr(str); return this; }
    MonsterBuilder* dex(unsigned int dex) { this->newMonster->setDex(dex); return this; }
    MonsterBuilder* con(unsigned int con) { this->newMonster->setCon(con); return this; }
    MonsterBuilder* intell(unsigned int intell) { this->newMonster->setInt(intell); return this; }
    MonsterBuilder* wis(unsigned int wis) { this->newMonster->setWis(wis); return this; }
    MonsterBuilder* cha(unsigned int cha) { this->newMonster->setCha(cha); return this; }

    MonsterBuilder* vulnerabilities(const std::string &vulnerability) { this->newMonster->setVulnerabilities(vulnerability); return this; }
    MonsterBuilder* resistances(const std::string &resistance) { this->newMonster->setResistances(resistance); return this; }
    MonsterBuilder* damageImmunities(const std::string &immunity) { this->newMonster->setDamageImmunities(immunity); return this; }
    MonsterBuilder* conditionImmunities(const std::string &conditionImmunity) { this->newMonster->setConditionImmunities(conditionImmunity); return this; }
    MonsterBuilder* senses(const std::string &senses) { this->newMonster->setSenses(senses); return this; }
    MonsterBuilder* languages(const std::string &languages) { this->newMonster->setLanguages(languages); return this; }
    MonsterBuilder* cr(float cr) { this->newMonster->setCR(cr); return this; }

    MonsterBuilder* addSave(const std::string &name, const int value) { this->newMonster->addSave(name, value); return this; }
    MonsterBuilder* addSkill(const std::string &name, const int value) { this->newMonster->addSkill(name, value); return this; }
    MonsterBuilder* addTrait(const std::string &name, const std::string &desc) { this->newMonster->addTrait(name, desc); return this; }
    MonsterBuilder* addAction(const std::string &name, const std::string &desc) { this->newMonster->addAction(name, desc); return this; }
    MonsterBuilder* addReaction(const std::string &name, const std::string &desc) { this->newMonster->addReaction(name, desc); return this; }
    MonsterBuilder* addLegendaryAction(const std::string &name, const std::string &desc) { this->newMonster->addLegendaryAction(name, desc); return this; }

    MonsterBuilder* spellListDesc(const std::string &desc) { this->newMonster->setSpellListDescription(desc); return this; }
    MonsterBuilder* cantrips(const std::string &cantrips) { this->newMonster->setCantrips(cantrips); return this; }
    MonsterBuilder* level1(const std::string &level1) { this->newMonster->setLevel1(level1); return this; }
    MonsterBuilder* level2(const std::string &level2) { this->newMonster->setLevel2(level2); return this; }
    MonsterBuilder* level3(const std::string &level3) { this->newMonster->setLevel3(level3); return this; }
    MonsterBuilder* level4(const std::string &level4) { this->newMonster->setLevel4(level4); return this; }
    MonsterBuilder* level5(const std::string &level5) { this->newMonster->setLevel5(level5); return this; }
    MonsterBuilder* level6(const std::string &level6) { this->newMonster->setLevel6(level6); return this; }
    MonsterBuilder* level7(const std::string &level7) { this->newMonster->setLevel7(level7); return this; }
    MonsterBuilder* level8(const std::string &level8) { this->newMonster->setLevel8(level8); return this; }
    MonsterBuilder* level9(const std::string &level9) { this->newMonster->setLevel9(level9); return this; }

    Monster* build() { return this->newMonster; }

    static void tests();

private:
    Monster *newMonster;
};


#endif //MONSTER_LIBRARY_MONSTERBUILDER_H
