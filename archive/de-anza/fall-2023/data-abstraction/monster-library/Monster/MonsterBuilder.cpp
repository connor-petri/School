//
// Created by Connor Petri on 10/8/23.
//

#include "MonsterBuilder.h"
#include <exception>

MonsterBuilder* MonsterBuilder::create(const std::string &name)
{
    MonsterBuilder *builder = new MonsterBuilder();
    builder->newMonster = new Monster(name);
    return builder;
}

void MonsterBuilder::tests()
{
    MonsterBuilder *builder = MonsterBuilder::create("Test Monster");
    Monster *monster = builder->size("Medium")
            ->type("Humanoid")
            ->alignment("Chaotic Evil")
            ->ac(15)
            ->hp(100)
            ->hitDice("10d10")
            ->speed("30 ft.")
            ->str(10)
            ->dex(10)
            ->con(10)
            ->intell(10)
            ->wis(10)
            ->cha(10)
            ->vulnerabilities("Fire")
            ->resistances("Cold")
            ->damageImmunities("Poison")
            ->conditionImmunities("Blinded")
            ->senses("Darkvision 60 ft.")
            ->languages("Common")
            ->cr(1.0f)
            ->addSave("Strength", 0)
            ->addSkill("Athletics", 0)
            ->addTrait("Innate Spellcasting", (std::string)"The monster's innate spellcasting ability is Charisma (spell save DC 12). It can innately cast the following spells, requiring no material components:\n" +
                                              "At will: detect magic, disguise self\n" +
                                              "1/day each: charm person, detect thoughts, suggestion")
            ->addAction("Multiattack", "The monster makes two melee attacks.")
            ->addAction("Greatsword", "Melee Weapon Attack: +5 to hit, reach 5 ft., one target. Hit: 10 (2d6 + 3) slashing damage.")
            ->addReaction("Parry", "The monster adds 2 to its AC against one melee attack that would hit it. To do so, the monster must see the attacker and be wielding a melee weapon.")
            ->addLegendaryAction("Move", "The monster moves up to its speed.")
            ->addLegendaryAction("Attack", "The monster makes one melee attack.")
            ->addLegendaryAction("Wing Attack (Costs 2 Actions)", "The monster beats its wings. Each creature within 10 ft. of the monster must succeed on a DC 12 Dexterity saving throw or take 7 (2d6) bludgeoning damage and be knocked prone. The monster can then fly up to half its flying speed.")
            ->spellListDesc("The monster is a 4th-level spellcaster. Its spellcasting ability is Intelligence (spell save DC 12, +4 to hit). The monster has the following wizard spells prepared:")
            ->cantrips("fire bolt, light, mage hand, prestidigitation")
            ->level1("detect magic, mage armor, magic missile, shield")
            ->level2("misty step, suggestion")
            ->level3("counterspell, fireball")
            ->build();

    if (monster->getName() != "Test Monster")
    { throw std::runtime_error("MonsterBuilder::create() failed to set name."); }

    if (monster->getSize() != "Medium")
    { throw std::runtime_error("MonsterBuilder::size() failed to set size."); }

    if (monster->getType() != "Humanoid")
    { throw std::runtime_error("MonsterBuilder::type() failed to set type."); }

    if (monster->getAlignment() != "Chaotic Evil")
    { throw std::runtime_error("MonsterBuilder::alignment() failed to set alignment."); }

    if (monster->getAC() != 15)
    { throw std::runtime_error("MonsterBuilder::ac() failed to set AC."); }

    if (monster->getHP() != 100)
    { throw std::runtime_error("MonsterBuilder::hp() failed to set HP."); }

    if (monster->getHitDice() != "10d10")
    { throw std::runtime_error("MonsterBuilder::hitDice() failed to set hit dice."); }

    if (monster->getSpeed() != "30 ft.")
    { throw std::runtime_error("MonsterBuilder::speed() failed to set speed."); }

    if (monster->getStr() != 10)
    { throw std::runtime_error("MonsterBuilder::str() failed to set strength."); }

    if (monster->getDex() != 10)
    { throw std::runtime_error("MonsterBuilder::dex() failed to set dexterity."); }

    if (monster->getCon() != 10)
    { throw std::runtime_error("MonsterBuilder::con() failed to set constitution."); }

    if (monster->getInt() != 10)
    { throw std::runtime_error("MonsterBuilder::intell() failed to set intelligence."); }

    if (monster->getWis() != 10)
    { throw std::runtime_error("MonsterBuilder::wis() failed to set wisdom."); }

    if (monster->getCha() != 10)
    { throw std::runtime_error("MonsterBuilder::cha() failed to set charisma."); }

    if (monster->getVulnerabilities() != "Fire")
    { throw std::runtime_error("MonsterBuilder::vulnerabilities() failed to set vulnerabilities."); }

    if (monster->getResistances() != "Cold")
    { throw std::runtime_error("MonsterBuilder::resistances() failed to set resistances."); }

    if (monster->getDamageImmunities() != "Poison")
    { throw std::runtime_error("MonsterBuilder::damageImmunities() failed to set damage immunities."); }

    if (monster->getConditionImmunities() != "Blinded")
    { throw std::runtime_error("MonsterBuilder::conditionImmunities() failed to set condition immunities."); }

    if (monster->getSenses() != "Darkvision 60 ft.")
    { throw std::runtime_error("MonsterBuilder::senses() failed to set senses."); }

    if (monster->getLanguages() != "Common")
    { throw std::runtime_error("MonsterBuilder::languages() failed to set languages."); }

    if (monster->getCR() != 1.0f)
    { throw std::runtime_error("MonsterBuilder::cr() failed to set CR."); }

    if (monster->getSaves().size() != 1)
    { throw std::runtime_error("MonsterBuilder::addSave() failed to add save."); }

    if (monster->getSkills().size() != 1)
    { throw std::runtime_error("MonsterBuilder::addSkill() failed to add skill."); }

    if (monster->getTraits().size() != 1)
    { throw std::runtime_error("MonsterBuilder::addTrait() failed to add trait."); }

    if (monster->getActions().size() != 2)
    { throw std::runtime_error("MonsterBuilder::addAction() failed to add action."); }

    if (monster->getReactions().size() != 1)
    { throw std::runtime_error("MonsterBuilder::addReaction() failed to add reaction."); }

    if (monster->getLegendaryActions().size() != 3)
    { throw std::runtime_error("MonsterBuilder::addLegendaryAction() failed to add legendary action."); }

    if (*monster->getSpellList().description != "The monster is a 4th-level spellcaster. Its spellcasting ability is Intelligence (spell save DC 12, +4 to hit). The monster has the following wizard spells prepared:")
    { throw std::runtime_error("MonsterBuilder::spellListDesc() failed to set spell list description."); }

    if (*monster->getSpellList().cantrips != "fire bolt, light, mage hand, prestidigitation")
    { throw std::runtime_error("MonsterBuilder::cantrips() failed to set cantrips."); }

    if (*monster->getSpellList().level1 != "detect magic, mage armor, magic missile, shield")
    { throw std::runtime_error("MonsterBuilder::level1() failed to set level 1 spells."); }

    if (*monster->getSpellList().level2 != "misty step, suggestion")
    { throw std::runtime_error("MonsterBuilder::level2() failed to set level 2 spells."); }

    if (*monster->getSpellList().level3 != "counterspell, fireball")
    { throw std::runtime_error("MonsterBuilder::level3() failed to set level 3 spells."); }

    delete monster;
}