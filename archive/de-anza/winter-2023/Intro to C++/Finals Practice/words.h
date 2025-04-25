//
// Created by Connor Petri on 3/17/23.
//
#pragma once

#include <string>
#include <vector>
#include <tuple>

namespace words
{

    class Word
    {

    private:
        std::string _word;
        std::string _definition;

    public:
        Word(std::string word, std::string def);

        std::string word() const;
        void word(std::string word);

        std::string definition() const;
        void definition(std::string def);

        bool operator==(const Word& rhs) const;
        bool operator!=(const Word& rhs) const;

        std::string toString() const;
        bool isTheSameWord(const Word& word2) const;
        bool find(const std::string& searchString) const;

    };

    Word* createWord(std::string word, std::string def);
    std::tuple<int, int> checkDuplicate(Word** wordList, int size, Word& searchWord);
    int removeThisWord(std::vector<Word*> wordList, Word& purgeWord);

    //Write a function named “hasOnlyQuestionMark” that accepts an array of C-string pointers and its size.
    int hasOnlyQuestionMark(char** cstringList, int size);

    bool WordTestCases();

}

