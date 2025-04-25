//
// Created by Connor Petri on 10/31/23.
//

#ifndef HASHTABLE_PREP_HASHTABLE_H
#define HASHTABLE_PREP_HASHTABLE_H
#include <vector>
#include <string>

//Student class to be collected.
class Student
{
private:
    std::string *_name;
    unsigned int _id;

public:
    Student(const std::string& name, const unsigned int id)
    {
        _name = new std::string(name);
        _id = id;
    }

    ~Student()
    {
        delete _name;
    }

    std::string getName() const { return *_name; }
    unsigned int getId() const { return _id; }

    void setName(const std::string& name) { *_name = name; }
    void setId(const unsigned int id) { _id = id; }
};

template <class T>
struct HNode
{
    std::string key;
    T value;
};

template <class T>
class Bucket
{
private:
    std::vector<HNode<T>*> *_nodes;

public:
    Bucket()
    {
        _nodes = new std::vector<HNode<T>*>();
    }

    Bucket(const Bucket<T> &b)
    {
        _nodes = b._nodes;
    }

    ~Bucket()
    {
        for (int i = 0; i < _nodes->size(); i++)
        {
            delete _nodes[i];
        }
        delete _nodes;
    }

    void addNode(const std::string &key, const T &value)
    {
        HNode<T> *node = new HNode<T>();
        node->key = key;
        node->value = value;
        _nodes->push_back(node);
    }

    void deleteNode(const std::string &key)
    {
        for (int i = 0; i < _nodes->size(); i++)
        {
            if (_nodes->at(i)->key == key)
            {
                _nodes->erase(i);
                return;
            }
        }
    }

    HNode<T> * getNode(const std::string& key)
    {
        for (HNode<T> *node : _nodes)
        {
            if (node->key == key) { return node; }
        }

        return nullptr;
    }

    size_t size() { return _nodes->size(); }
};

template <class T>
class Hashtable
{
protected:
    Bucket<T> **_buckets;
    unsigned int _numBuckets;

    unsigned int _hash(const std::string &key)
    {
        int sum = 0;
        for (char c : key)
        {
            sum += (int)c;
        }

        return sum % _numBuckets;
    }

public:
    Hashtable(const unsigned int numBuckets = 50)
    {
        *_buckets = new Bucket<T>[_numBuckets];
        _numBuckets = numBuckets;
    }

    Hashtable(const Hashtable &ht)
    {
        _buckets = ht._buckets;
        _numBuckets = ht._numBuckets;
    }

    ~Hashtable()
    {
        delete[] _buckets;
    }

    T & operator[](const std::string &key)
    {
        return _buckets[_hash(key)]->getNode(key)->value;
    }

};


#endif //HASHTABLE_PREP_HASHTABLE_H
