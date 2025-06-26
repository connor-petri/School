#include <iostream>
#include <vector>

template <typename T>
class Node
{
private:
    T _value;
    Node<T>* _next;

public:
    explicit Node(const T& value, const Node* next = nullptr) : _value(value), _next(next) {}

    T getValue() const { return this->_value; }
    Node<T>* getNext() const { return this->_next; }
    void setValue(const T& value) { this->_value = value; }
    void setNext(const Node* next) { this->_next = next; }
};


template <typename T>
class LinkedList
{
private:
    Node<T>* _head;
    Node<T>* _tail;

    Node<T>* _nodes;
    int _size;

    void push_back(const T& value)
    {
        Node<T> temp[this->_size] = this->_nodes;
        delete[] this->_nodes;
        this->_size++;
        this->_nodes = new Node<T>[this->_size];

        for (int i = 0; i < this->_size - 1; i++)
        {
            this->_nodes[i] = Node<T>(temp)
        }
    }

    void push_front(const T& value)
    {
        Node<T> temp[this->_size] = this->_nodes;
        delete[] this->_nodes;
        this->_size++;
        this->_nodes = new Node<T>[this->_size];
        this->_nodes[0] = Node<T>(value);
    }

public:
    explicit LinkedList() : _head(nullptr), _tail(nullptr), _nodes(new Node<T>[0]), _size(0) {}
    ~LinkedList() { delete[] this->_nodes; }

    int size() const { return this->_size; }



};


void main()
{
    return 0;
}
