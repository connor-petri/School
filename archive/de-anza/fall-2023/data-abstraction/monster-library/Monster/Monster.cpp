//
// Created by Connor Petri on 10/6/23.
//

#include "Monster.h"
#include <exception>

Monster::Monster(const std::string &name)
{
    *this->name = name;
}

Monster::Monster(const Monster &monster)
{
    *this->name = monster.getName();
    *this->size = monster.getSize();
    *this->type = monster.getType();
    *this->alignment = monster.getAlignment();
    *this->ac = monster.getAC();
    *this->hp = monster.getHP();
    *this->speed = monster.getSpeed();
    *this->hitDice = monster.getHitDice();
    *this->stats->str = monster.getStr();
    *this->stats->dex = monster.getDex();
    *this->stats->con = monster.getCon();
    *this->stats->intell = monster.getInt();
    *this->stats->wis = monster.getWis();
    *this->stats->cha = monster.getCha();
    *this->stats->strMod = monster.getStrMod();
    *this->stats->dexMod = monster.getDexMod();
    *this->stats->conMod = monster.getConMod();
    *this->stats->intellMod = monster.getIntMod();
    *this->stats->wisMod = monster.getWisMod();
    *this->stats->chaMod = monster.getChaMod();

    for (auto *save : monster.getSaves())
    {
        this->saves->push_back(save);
    }

    for (auto *skill : monster.getSkills())
    {
        this->skills->push_back(skill);
    }

    for (auto *trait : monster.getTraits())
    {
        this->traits->push_back(trait);
    }

    for (auto *action : monster.getActions())
    {
        this->actions->push_back(action);
    }

    for (auto *reaction : monster.getReactions())
    {
        this->reactions->push_back(reaction);
    }

    for (auto *legendaryActions : monster.getLegendaryActions())
    {
        this->legendaryActions->push_back(legendaryActions);
    }

    *this->vulnerabilities = monster.getVulnerabilities();
    *this->resistances = monster.getResistances();
    *this->damageImmunities = monster.getDamageImmunities();
    *this->conditionImmunities = monster.getConditionImmunities();
    *this->senses = monster.getSenses();
    *this->languages = monster.getLanguages();
    *this->cr = monster.getCR();

    *this->spellList->cantrips = *monster.getSpellList().cantrips;
    *this->spellList->level1 = *monster.getSpellList().level1;
    *this->spellList->level2 = *monster.getSpellList().level2;
    *this->spellList->level3 = *monster.getSpellList().level3;
    *this->spellList->level4 = *monster.getSpellList().level4;
    *this->spellList->level5 = *monster.getSpellList().level5;
    *this->spellList->level6 = *monster.getSpellList().level6;
    *this->spellList->level7 = *monster.getSpellList().level7;
    *this->spellList->level8 = *monster.getSpellList().level8;
    *this->spellList->level9 = *monster.getSpellList().level9;
}

Monster::~Monster()
{
    delete this->name;
    delete this->size;
    delete this->type;
    delete this->alignment;
    delete this->ac;
    delete this->hp;
    delete this->hitDice;
    delete this->speed;
    delete this->stats->str;
    delete this->stats->dex;
    delete this->stats->con;
    delete this->stats->intell;
    delete this->stats->wis;
    delete this->stats->cha;
    delete this->stats->strMod;
    delete this->stats->dexMod;
    delete this->stats->conMod;
    delete this->stats->intellMod;
    delete this->stats->wisMod;
    delete this->stats->chaMod;
    delete this->stats;

    for (auto *save : *this->saves)
    {
        delete save->name;
        delete save->value;
        delete save;
    }
    delete this->saves;

    for (auto *skill : *this->skills)
    {
        delete skill->name;
        delete skill->value;
        delete skill;
    }
    delete this->skills;

    for (auto *trait : *this->traits)
    {
        delete trait->name;
        delete trait->desc;
        delete trait;
    }
    delete this->traits;

    for (auto *action : *this->actions)
    {
        delete action->name;
        delete action->desc;
        delete action;
    }
    delete this->actions;

    for (auto *reaction : *this->reactions)
    {
        delete reaction->name;
        delete reaction->desc;
        delete reaction;
    }
    delete this->reactions;

    for (auto *legendaryAction : *this->legendaryActions)
    {
        delete legendaryAction->name;
        delete legendaryAction->desc;
        delete legendaryAction;
    }
    delete this->legendaryActions;

    delete this->vulnerabilities;
    delete this->resistances;
    delete this->damageImmunities;
    delete this->conditionImmunities;
    delete this->senses;
    delete this->languages;
    delete this->cr;

    delete this->spellList->description;
    delete this->spellList->cantrips;
    delete this->spellList->level1;
    delete this->spellList->level2;
    delete this->spellList->level3;
    delete this->spellList->level4;
    delete this->spellList->level5;
    delete this->spellList->level6;
    delete this->spellList->level7;
    delete this->spellList->level8;
    delete this->spellList->level9;
    delete this->spellList;
}


void Monster::addSave(const std::string &name, int value)
{
    Skill *skill = new Skill;
    skill->name = new std::string(name);
    skill->value = new int(value);
    this->saves->push_back(skill);
}


void Monster::addSkill(const std::string &name, int value)
{
    Skill *skill = new Skill;
    skill->name = new std::string(name);
    skill->value = new int(value);
    this->skills->push_back(skill);
}


void Monster::addTrait(const std::string &name, const std::string &desc)
{
    Trait *trait = new Trait;
    trait->name = new std::string(name);
    trait->desc = new std::string(desc);
    this->traits->push_back(trait);
}


void Monster::addAction(const std::string &name, const std::string &desc)
{
    Trait *action = new Trait;
    action->name = new std::string(name);
    action->desc = new std::string(desc);
    this->actions->push_back(action);
}


void Monster::addReaction(const std::string &name, const std::string &desc)
{
    Trait *reaction = new Trait;
    reaction->name = new std::string(name);
    reaction->desc = new std::string(desc);
    this->reactions->push_back(reaction);
}


void Monster::addLegendaryAction(const std::string &name, const std::string &desc)
{
    Trait *legendaryAction = new Trait;
    legendaryAction->name = new std::string(name);
    legendaryAction->desc = new std::string(desc);
    this->legendaryActions->push_back(legendaryAction);
}


void Monster::tests()
{
    Monster monster("Test Monster");
    monster.setSize("Medium");
    monster.setType("Humanoid");
    monster.setAlignment("Neutral");

    monster.setAC(10);
    monster.setHP(10);
    monster.setHitDice("1d10");
    monster.setSpeed("30 ft.");

    monster.setStr(10);
    monster.setDex(10);
    monster.setCon(10);
    monster.setInt(10);
    monster.setWis(10);
    monster.setCha(10);

    monster.addSave("Strength", 0);
    monster.addSave("Dexterity", 0);
    monster.addSave("Constitution", 0);
    monster.addSave("Intelligence", 0);
    monster.addSave("Wisdom", 0);
    monster.addSave("Charisma", 0);

    monster.addSkill("Acrobatics", 0);
    monster.addSkill("History", 0);
    monster.addSkill("Stealth", 0);
    monster.addSkill("Survival", 0);

    monster.addTrait("Test Trait", "Test Trait Description");
    monster.addAction("Test Action", "Test Action Description");
    monster.addReaction("Test Reaction", "Test Reaction Description");
    monster.addLegendaryAction("Test Legendary Action", "Test Legendary Action Description");

    monster.setVulnerabilities("Test Vulnerabilities");
    monster.setResistances("Test Resistances");
    monster.setDamageImmunities("Test Damage Immunities");
    monster.setConditionImmunities("Test Condition Immunities");
    monster.setSenses("Test Senses");
    monster.setLanguages("Test Languages");
    monster.setCR(0);

    monster.setSpellListDescription("Test Spell List Description");
    monster.setCantrips("Test Cantrips");
    monster.setLevel1("Test Level 1");
    monster.setLevel2("Test Level 2");
    monster.setLevel3("Test Level 3");
    monster.setLevel4("Test Level 4");
    monster.setLevel5("Test Level 5");
    monster.setLevel6("Test Level 6");
    monster.setLevel7("Test Level 7");
    monster.setLevel8("Test Level 8");
    monster.setLevel9("Test Level 9");

    if (monster.getName() != "Test Monster")
    { throw std::runtime_error("Monster::getName() failed."); }

    if (monster.getSize() != "Medium")
    { throw std::runtime_error("Monster::getSize() failed."); }

    if (monster.getType() != "Humanoid")
    { throw std::runtime_error("Monster::getType() failed."); }

    if (monster.getAlignment() != "Neutral")
    { throw std::runtime_error("Monster::getAlignment() failed."); }

    if (monster.getAC() != 10)
    { throw std::runtime_error("Monster::getAC() failed."); }

    if (monster.getHP() != 10)
    { throw std::runtime_error("Monster::getHP() failed."); }

    if (monster.getHitDice() != "1d10")
    { throw std::runtime_error("Monster::getHitDice() failed."); }

    if (monster.getSpeed() != "30 ft.")
    { throw std::runtime_error("Monster::getSpeed() failed."); }

    if (monster.getStr() != 10)
    { throw std::runtime_error("Monster::getStr() failed."); }

    if (monster.getDex() != 10)
    { throw std::runtime_error("Monster::getDex() failed."); }

    if (monster.getCon() != 10)
    { throw std::runtime_error("Monster::getCon() failed."); }

    if (monster.getInt() != 10)
    { throw std::runtime_error("Monster::getInt() failed."); }

    if (monster.getWis() != 10)
    { throw std::runtime_error("Monster::getWis() failed."); }

    if (monster.getCha() != 10)
    { throw std::runtime_error("Monster::getCha() failed."); }

    if (monster.getStrMod() != 0)
    { throw std::runtime_error("Monster::getStrMod() failed."); }

    if (monster.getDexMod() != 0)
    { throw std::runtime_error("Monster::getDexMod() failed."); }

    if (monster.getConMod() != 0)
    { throw std::runtime_error("Monster::getConMod() failed."); }

    if (monster.getIntMod() != 0)
    { throw std::runtime_error("Monster::getIntMod() failed."); }

    if (monster.getWisMod() != 0)
    { throw std::runtime_error("Monster::getWisMod() failed."); }

    if (monster.getChaMod() != 0)
    { throw std::runtime_error("Monster::getChaMod() failed."); }

    for (auto *save : monster.getSaves())
    {
        if (*save->name != "Strength" && *save->name != "Dexterity" && *save->name != "Constitution" &&
            *save->name != "Intelligence" && *save->name != "Wisdom" && *save->name != "Charisma")
        { throw std::runtime_error("Monster::getSaves() failed."); }

        if (*save->value != 0)
        { throw std::runtime_error("Monster::getSaves() failed."); }
    }

    for (auto *skill : monster.getSkills())
    {
        if (*skill->name != "Acrobatics" && *skill->name != "History" && *skill->name != "Stealth" &&
            *skill->name != "Survival")
        { throw std::runtime_error("Monster::getSkills() failed."); }

        if (*skill->value != 0)
        { throw std::runtime_error("Monster::getSkills() failed."); }
    }

    if (*monster.getTraits().at(0)->name != "Test Trait")
    { throw std::runtime_error("Monster::getTraits() failed."); }

    if (*monster.getTraits().at(0)->desc != "Test Trait Description")
    { throw std::runtime_error("Monster::getTraits() failed."); }

    if (*monster.getActions().at(0)->name != "Test Action")
    { throw std::runtime_error("Monster::getActions() failed."); }

    if (*monster.getActions().at(0)->desc != "Test Action Description")
    { throw std::runtime_error("Monster::getActions() failed."); }

    if (*monster.getReactions().at(0)->name != "Test Reaction")
    { throw std::runtime_error("Monster::getReactions() failed."); }

    if (*monster.getReactions().at(0)->desc != "Test Reaction Description")
    { throw std::runtime_error("Monster::getReactions() failed."); }

    if (*monster.getLegendaryActions().at(0)->name != "Test Legendary Action")
    { throw std::runtime_error("Monster::getLegendaryActions() failed."); }

    if (*monster.getLegendaryActions().at(0)->desc != "Test Legendary Action Description")
    { throw std::runtime_error("Monster::getLegendaryActions() failed."); }

    if (monster.getVulnerabilities() != "Test Vulnerabilities")
    { throw std::runtime_error("Monster::getVulnerabilities() failed."); }

    if (monster.getResistances() != "Test Resistances")
    { throw std::runtime_error("Monster::getResistances() failed."); }

    if (monster.getDamageImmunities() != "Test Damage Immunities")
    { throw std::runtime_error("Monster::getDamageImmunities() failed."); }

    if (monster.getConditionImmunities() != "Test Condition Immunities")
    { throw std::runtime_error("Monster::getConditionImmunities() failed."); }

    if (monster.getSenses() != "Test Senses")
    { throw std::runtime_error("Monster::getSenses() failed."); }

    if (monster.getLanguages() != "Test Languages")
    { throw std::runtime_error("Monster::getLanguages() failed."); }

    if (monster.getCR() != 0)
    { throw std::runtime_error("Monster::getCR() failed."); }

    if (*monster.getSpellList().description != "Test Spell List Description")
    { throw std::runtime_error("Monster::getSpellList() failed."); }

    if (*monster.getSpellList().cantrips != "Test Cantrips")
    { throw std::runtime_error("Monster::getSpellList() failed."); }

    if (*monster.getSpellList().level1 != "Test Level 1")
    { throw std::runtime_error("Monster::getSpellList() failed."); }

    if (*monster.getSpellList().level2 != "Test Level 2")
    { throw std::runtime_error("Monster::getSpellList() failed."); }

    if (*monster.getSpellList().level3 != "Test Level 3")
    { throw std::runtime_error("Monster::getSpellList() failed."); }

    if (*monster.getSpellList().level4 != "Test Level 4")
    { throw std::runtime_error("Monster::getSpellList() failed."); }

    if (*monster.getSpellList().level5 != "Test Level 5")
    { throw std::runtime_error("Monster::getSpellList() failed."); }

    if (*monster.getSpellList().level6 != "Test Level 6")
    { throw std::runtime_error("Monster::getSpellList() failed."); }

    if (*monster.getSpellList().level7 != "Test Level 7")
    { throw std::runtime_error("Monster::getSpellList() failed."); }

    if (*monster.getSpellList().level8 != "Test Level 8")
    { throw std::runtime_error("Monster::getSpellList() failed."); }

    if (*monster.getSpellList().level9 != "Test Level 9")
    { throw std::runtime_error("Monster::getSpellList() failed."); }

}