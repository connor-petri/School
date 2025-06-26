// Connor Petri
// Professor Kleinman
// CIS 22C
// 2023-12-12

#include <string>

enum eDir { TO, FROM };

struct Edge
{
    std::string start;
    std::string end;
    double weight;
};


class ESA
{
public:
    ESA(int size);
    void append(std::string &name);
    std::string pop();
    std::string get(int index);
    int getSize();
};

class EGA
{
public:
    unsigned int getNum(); // Find # of edges in collection (size)
    Edge get(int index); // Get edge at index position in EGA (or NULL)
};

class Graph
{
public:
    bool hasEdge(std::string &start, std::string &end, enum eDir ed);
    EGA * getEdges(std::string &start, enum eDir ed);

    ESA * getVerticies(Graph *graph, std::string &start); // Returns all reachable verticies from start
};


ESA * Graph::getVerticies(Graph* graph, std::string &start)
{
    // ESAs are FIFO and use append() and pop() to add and remove elements from the back of ESA
    ESA *discovered = new ESA(1000);
    ESA *visited = new ESA(1000);

    if (!graph->getEdges(start, eDir::FROM))
    {
        return nullptr;
    }

    discovered->append(start);

    while (discovered->getSize())
    {
        std::string vName = discovered->pop();

        bool matchFound = false;
        for (int i = 0; i < visited->getSize(); i++)
        {
            if (vName == visited->get(i))
            {
                matchFound = true;
                break;
            }
        }

        if (matchFound) { continue; }

        visited->append(vName);

        EGA *edges = graph->getEdges(vName, eDir::FROM);

        for (int i = 0; i < edges->getNum(); i++)
        {
            std::string end = edges->get(i).end;
            discovered->append(end);
        }
    }

    return visited;
}



class Person
{
private:
    std::string name;
    int age;

public:
    Person(std::string& name, int age)
    {
        this->name = name;
        this->age = age;
    }

    std::string getName()
    {
        return name;
    }
};

int main()
{
    Person test = Person("Connor", 22);

    test.getName();
}