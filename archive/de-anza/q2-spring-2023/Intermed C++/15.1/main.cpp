#include <iostream>
#include <string>

class Book
{
protected:
    std::string _title;
    std::string _author;
public:
    Book(const std::string& title, const std::string& author)
            : _title(title), _author(author) {}

    std::string getTitle() const { return this->_title; }
    std::string getAuthor() const { return this->_author; }
    void setTitle(const std::string& title) { this->_title = title; }
    void setAuthor(const std::string& author) { this->_author = author; }

    void printData()
    {
        std::cout << "Book is " << this->_title << " by " << this->_author << "\n";
    }
};

class Fiction: private Book
{
private:
    int _level;
public:
    Fiction(const std::string& title, const std::string& author, int level)
            : Book(title, author), _level(level) {}

    int getLevel() const { return this->_level; }
    void setLevel(int level) { this->_level = level; }

    void printData()
    {
        Book::printData();
        std::cout << "Reading level is: " << this->_level << "\n";
    }
};

class NonFiction: private Book
{
private:
    int _pageCount;
public:
    NonFiction(const std::string& title, const std::string& author, int pageCount)
            : Book(title, author), _pageCount(pageCount) {}

    int getPageCount() const { return this->_pageCount; }
    void setPageCount(int pageCount) { this->_pageCount = pageCount; }

    void printData()
    {
        Book::printData();
        std::cout << this->_pageCount << " pages\n";
    }
};



int main()
{

    Fiction fiction("Scarlet Letter", "Hawthorne", 10);
    NonFiction nonFiction("Log Cabin", "Landers", 328);

    fiction.printData();
    nonFiction.printData();

    return 0;

}
