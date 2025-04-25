//
// Created by Connor Petri on 3/17/23.
//

#include "words.h"
#include <string>
#include <vector>
#include <tuple>
#include <iostream>

using namespace words;

// Constructor
Word::Word(std::string word, std::string def)
    : _word(std::move(word)), _definition(std::move(def)) {}

std::string Word::word() const
{
    return this->_word;
}


// Getters and setters
void Word::word(std::string word)
{
    this->_word = std::move(word);
}

std::string Word::definition() const
{
    return this->_definition;
}

void Word::definition(std::string def)
{
    this->_definition = std::move(def);
}


// Operator overloading for == and !=
bool Word::operator==(const Word& rhs) const
{
    return (this->word() == rhs.word());
}

bool Word::operator!=(const Word& rhs) const
{
    return !(*this == rhs);
}


// Member Functions

std::string Word::toString() const
{
    return "WORD(" + this->word() + ") DEFINITION(" + this->definition() + ")";
}


bool Word::isTheSameWord(const Word& word2) const
{
    return (*this == word2);
}


bool Word::find(const std::string& searchString) const
{
    if (this->word().find(searchString) != std::string::npos || this->definition().find(searchString) != std::string::npos)
    {
        return true;
    }
    return false;
}


// Other Functions
Word* words::createWord(std::string word, std::string def)
{
    return new Word(word, def);
}


std::tuple<int, int> words::checkDuplicate(Word** wordList, int size, Word &searchWord)
{

    int numWordMatch = 0;
    int numWordAndDefMatch = 0;

    for (int i = 0; i < size; i++)
    {

        if (*wordList[i] == searchWord)
        {

            numWordMatch++;
            if (wordList[i]->definition() == searchWord.definition())
            {
                numWordAndDefMatch++;
            }

        }

    }

    return std::make_tuple(numWordMatch, numWordAndDefMatch);

}


int words::removeThisWord(std::vector<Word*> wordList, Word &purgeWord)
{

    int numRemoved = 0;

    for (int i = 0; i < wordList.size(); i++)
    {
        if (*wordList.at(i) == purgeWord)
        {
            wordList.erase(wordList.begin() + i);
            numRemoved++;
        }
    }

    return numRemoved;

}


int words::hasOnlyQuestionMark(char **cstringList, int size)
{

    int numAllQ = 0;
    bool isAllQ;

    for (int i = 0; i < size; i++)
    {

        isAllQ = true;

        for (int j = 0; j < std::strlen(cstringList[i]); j++)
        {
            if (cstringList[i][j] != '?')
            {
                isAllQ = false;
            }

        }

        if (isAllQ)
            numAllQ++;

    }

    return numAllQ;

}


// Test Cases
bool words::WordTestCases()
{

    bool allPassed = true;
    std::vector<std::string> errors;

    // Tests for Constructor and getters/setters
    Word test1("test1", "test definition");

    if (test1.word() != "test1")
    {
        errors.emplace_back("Error initializing Word._word or error in Word.GetWord().");
        allPassed = false;
    }

    if (test1.definition() != "test definition")
    {
        errors.emplace_back("Error initializing Word._definition or error ins Word.GetDef().");
        allPassed = false;
    }

    test1.word("word1");
    test1.definition("def1");

    if (test1.word() != "word1")
    {
        errors.emplace_back("Error in Word.SetWord() (may be error in getter if that error is present too).");
        allPassed = false;
    }

    if (test1.definition() != "def1")
    {
        errors.emplace_back("Error in Word.SetDef() (may be error in getter if that error is present too).");
        allPassed = false;
    }

    // Tests for operator overloads
    Word test2("word2", "def2");
    Word test1dup("word1", "def1");

    if (!(test1 == test1dup))
    {
        errors.emplace_back("Error in == operator for Word.");
        allPassed = false;
    }

    if (!(test1 != test2))
    {
        errors.emplace_back("Error with operator != in Word.");
        allPassed = false;
    }


    // Tests for Member Functions
    if (test1.toString() != "WORD(word1) DEFINITION(def1)")
    {
        errors.emplace_back("Error in Word.toString().");
        allPassed = false;
    }

    if (!test1.find("1"))
    {
        errors.emplace_back("Error in Word.find().");
        allPassed = false;
    }


    Word* wordList[3] = { createWord("word1", "def1"), createWord("word1", "def0"), createWord("word2", "def2") };

    std::tuple<int, int> duplicateInts = checkDuplicate(wordList, 3, test1);

    if (get<0>(duplicateInts) != 2 || get<1>(duplicateInts) != 1)
    {
        errors.emplace_back("Error in checkDuplicates().");
        allPassed = false;
    }

    std::vector<Word*> wordVec;

    wordVec.emplace_back(createWord("word1", "def1"));
    wordVec.emplace_back(createWord("word1", "def0"));
    wordVec.emplace_back(createWord("word2", "def2"));

    if (removeThisWord(wordVec, test1) != 1)
    {
        errors.emplace_back("Error in removeThisWord().");
        allPassed = false;
    }


    char s1[3] = "??";
    char s2[4] = "Hi?";
    char s3 [2] = "?";
    char* arr[3] = { s1, s2, s3};

    if (hasOnlyQuestionMark(arr, 3) != 2)
    {
        errors.emplace_back("Error in hasOnlyQuestionMark().");
        allPassed = false;
    }


    if (!errors.empty())
    {
        std::cout << "Testing Errors:\n";
        for (int i = 0; i < errors.size(); i++)
        {
            std::cout << errors.at(i) << "\n";
        }
    }

    return allPassed;

}