//
// Created by Connor Petri on 10/6/23.
//

#ifndef MONSTER_LIBRARY_MONSTER_H
#define MONSTER_LIBRARY_MONSTER_H

#include <string>
#include <vector>

struct StatBlock
{
    unsigned int *str = new unsigned int;
    int *strMod = new int;

    unsigned int *dex = new unsigned int;
    int *dexMod = new int;

    unsigned int *con = new unsigned int;
    int *conMod = new int;

    unsigned int *intell = new unsigned int;
    int *intellMod = new int;

    unsigned int *wis = new unsigned int;
    int *wisMod = new int;

    unsigned int *cha = new unsigned int;
    int *chaMod = new int;
};

struct Skill
{
    std::string *name = new std::string;
    int *value = new int;
};

struct Trait
{
    std::string *name = new std::string;
    std::string *desc = new std::string;
};

struct SpellList
{
    std::string *description = new std::string;

    std::string *cantrips = new std::string;
    std::string *level1 = new std::string;
    std::string *level2 = new std::string;
    std::string *level3 = new std::string;
    std::string *level4 = new std::string;
    std::string *level5 = new std::string;
    std::string *level6 = new std::string;
    std::string *level7 = new std::string;
    std::string *level8 = new std::string;
    std::string *level9 = new std::string;
};

class Monster
{
public:
    Monster() {} // Default Constructor
    Monster(const std::string &name); // Constructor
    Monster(const Monster &monster); // Copy Constructor
    ~Monster(); // Destructor

    const std::string &getName() const { return *this->name; }
    const std::string &getSize() const { return *this->size; }
    const std::string &getType() const { return *this->type; }
    const std::string &getAlignment() const { return *this->alignment; }
    void setName(const std::string &name) { *this->name = name; }
    void setSize(const std::string &size) { *this->size = size; }
    void setType(const std::string &type) { *this->type = type; }
    void setAlignment(const std::string &alignment) { *this->alignment = alignment; }

    int getAC() const                               { return *this->ac; }
    int getHP() const                               { return *this->hp; }
    const std::string & getHitDice() const          { return *this->hitDice; }
    const std::string & getSpeed() const            { return *this->speed; }
    void setAC(int ac)                              { *this->ac = ac; }
    void setHP(int hp)                              { *this->hp = hp; }
    void setHitDice(const std::string &hitDice)     { *this->hitDice = hitDice; }
    void setSpeed(const std::string &speed)         { *this->speed = speed; }

    const unsigned int & getStr() const         { return *this->stats->str; }
    const int & getStrMod() const               { return *this->stats->strMod; }
    void setStr(const unsigned int &str)        { *this->stats->str = str; *this->stats->strMod = (str - 10) / 2; }

    const unsigned int & getDex() const         { return *this->stats->dex; }
    const int & getDexMod() const               { return *this->stats->dexMod; }
    void setDex(const unsigned int &dex)        { *this->stats->dex = dex; *this->stats->dexMod = (dex - 10) / 2; }

    const unsigned int & getCon() const         { return *this->stats->con; }
    const int & getConMod() const               { return *this->stats->conMod; }
    void setCon(const unsigned int &con)        { *this->stats->con = con; *this->stats->conMod = (con - 10) / 2; }

    const unsigned int & getInt() const         { return *this->stats->intell; }
    const int & getIntMod() const               { return *this->stats->intellMod; }
    void setInt(const unsigned int &intell)     { *this->stats->intell = intell; *this->stats->intellMod = (intell - 10) / 2; }

    const unsigned int & getWis() const         { return *this->stats->wis; }
    const int & getWisMod() const                { return *this->stats->wisMod; }
    void setWis(const unsigned int &wis)        { *this->stats->wis = wis; *this->stats->wisMod = (wis - 10) / 2; }

    const unsigned int & getCha() const         { return *this->stats->cha; }
    const int & getChaMod() const               { return *this->stats->chaMod; }
    void setCha(const unsigned int &cha)        { *this->stats->cha = cha; *this->stats->chaMod = (cha - 10) / 2; }

    const std::vector<Skill*> & getSaves() const { return *this->saves; }
    const std::vector<Skill*> & getSkills() const { return *this->skills; }
    const std::vector<Trait*> & getTraits() const { return *this->traits; }
    const std::vector<Trait*> & getActions() const { return *this->actions; }
    const std::vector<Trait*> & getReactions() const { return *this->reactions; }
    const std::vector<Trait*> & getLegendaryActions() const { return *this->legendaryActions; }

    void addSave(const std::string &name, const int value);
    void addSkill(const std::string &name, const int value);
    void addTrait(const std::string &name, const std::string &desc);
    void addAction(const std::string &name, const std::string &desc);
    void addReaction(const std::string &name, const std::string &desc);
    void addLegendaryAction(const std::string &name, const std::string &desc);

    const std::string & getVulnerabilities() const      { return *this->vulnerabilities; }
    const std::string & getResistances() const          { return *this->resistances; }
    const std::string & getDamageImmunities() const     { return *this->damageImmunities; }
    const std::string & getConditionImmunities() const  { return *this->conditionImmunities; }
    const std::string & getSenses() const               { return *this->senses; }
    const std::string & getLanguages() const            { return *this->languages; }
    float getCR() const { return *this->cr; }
    void setVulnerabilities(const std::string &vulnerabilities)             { *this->vulnerabilities = vulnerabilities; }
    void setResistances(const std::string &resistances)                     { *this->resistances = resistances; }
    void setDamageImmunities(const std::string &damageImmunities)           { *this->damageImmunities = damageImmunities; }
    void setConditionImmunities(const std::string &conditionImmunities)     { *this->conditionImmunities = conditionImmunities; }
    void setSenses(const std::string &senses)                               { *this->senses = senses; }
    void setLanguages(const std::string &languages)                         { *this->languages = languages; }
    void setCR(float cr)                                                      { *this->cr = cr; }

    const SpellList & getSpellList() const { return *this->spellList; }
    void setSpellListDescription(const std::string &description) { *this->spellList->description = description; }
    void setCantrips(const std::string &cantrips) { *this->spellList->cantrips = cantrips; }
    void setLevel1(const std::string &level1) { *this->spellList->level1 = level1; }
    void setLevel2(const std::string &level2) { *this->spellList->level2 = level2; }
    void setLevel3(const std::string &level3) { *this->spellList->level3 = level3; }
    void setLevel4(const std::string &level4) { *this->spellList->level4 = level4; }
    void setLevel5(const std::string &level5) { *this->spellList->level5 = level5; }
    void setLevel6(const std::string &level6) { *this->spellList->level6 = level6; }
    void setLevel7(const std::string &level7) { *this->spellList->level7 = level7; }
    void setLevel8(const std::string &level8) { *this->spellList->level8 = level8; }
    void setLevel9(const std::string &level9) { *this->spellList->level9 = level9; }

    static void tests();

protected:
    std::string *name = new std::string;
    std::string *size = new std::string;
    std::string *type = new std::string;
    std::string *alignment = new std::string;

    int          *ac = new int;
    int          *hp = new int;
    std::string  *hitDice = new std::string;
    std::string  *speed = new std::string;

    StatBlock           *stats = new StatBlock;
    std::vector<Skill*> *saves = new std::vector<Skill*>;
    std::vector<Skill*> *skills = new std::vector<Skill*>;

    std::string *vulnerabilities = new std::string;
    std::string *resistances = new std::string;
    std::string *damageImmunities = new std::string;
    std::string *conditionImmunities = new std::string;
    std::string *senses = new std::string;
    std::string *languages = new std::string;
    float       *cr = new float;

    std::vector<Trait*> *traits = new std::vector<Trait*>;
    std::vector<Trait*> *actions = new std::vector<Trait*>;
    std::vector<Trait*> *reactions = new std::vector<Trait*>;
    std::vector<Trait*> *legendaryActions = new std::vector<Trait*>;

    SpellList *spellList = new SpellList;
};

#endif //MONSTER_LIBRARY_MONSTER_H
