/*
 * Connor Petri
 * CIS 22C
 * 2023-10-24
 */

#include "ExtendableArray.h"

template <class T>
class Deque
{
protected:
    ExtendableArray<T> *arr;

public:
    Deque(unsigned int size = 0)
    {
        this->arr = new ExtendableArray<T>(size);
    }

    Deque(Deque &d)
    {
        this->arr = new ExtendableArray<T>(d.getSize());
        for (int i = 0; i < d.getSize(); i++)
        {
            this->arr->set(d.get(i), i);
        }
    }

    ~Deque()
    {
        delete this->arr;
    }

    int getSize()
    {
        return this->arr->getSize();
    }

    bool isEmpty()
    {
        return this->arr->getSize() == 0;
    }

    int pushFront(T *ptr)
    {
        return this->arr->prepend(ptr);
    }

    T * popFront()
    {
        T *temp = this->arr->get(0);
        this->arr->remove(0);
        return temp;
    }

    T * lookFront()
    {
        return this->arr->get(0);
    }

    int pushBack(T *ptr)
    {
        return this->arr->append(ptr);
    }

    T * popBack()
    {
        T *temp = this->arr->get(this->arr->getSize() - 1);
        this->arr->remove(this->arr->getSize() - 1);
        return temp;
    }

    T * lookBack()
    {
        return this->arr->get(this->arr->getSize() - 1);
    }

};

/* OUTPUT:
Read Array size 400  Read # commands 20
Command: Z  -1  -1
Empty : 1
Command: F  12345  KleinmanRonald
Pushed Front 12345  KleinmanRonald
Command: Z  -1  -1
Empty : 0
Command: B  87654  SmithMary
Pushed Back 87654  SmithMary
Command: B  54321  BerraYogi
Pushed Back 54321  BerraYogi
Command: S  -1  -1
Size:  3
Command: B  67890  RizzutoPhil
Pushed Back 67890  RizzutoPhil
Command: G  -1  -1
Pop Front 12345  KleinmanRonald
Command: G  -1  -1
Pop Front 87654  SmithMary
Command: Z  -1  -1
Empty : 0
Command: H  -1  -1
Look Front 54321  BerraYogi
Command: D  -1  -1
Look Back 67890  RizzutoPhil
Command: S  -1  -1
Size:  2
Command: F  67890  MantleMickey
Pushed Front 67890  MantleMickey
Command: B  89300  KoufaxSandy
Pushed Back 89300  KoufaxSandy
Command: B  89012  KoufaxSandy
Pushed Back 89012  KoufaxSandy
Command: F  99887  MarisRoger
Pushed Front 99887  MarisRoger
Command: S  -1  -1
Size:  6
Command: F  62109  FordWhitey
Pushed Front 62109  FordWhitey
Command: B  10200  SkowronMoose
Pushed Back 10200  SkowronMoose
------------------
Start Popping from bottom  8  Elements queued

Student: ID = 10200  Name = SkowronMoose
Student: ID = 89012  Name = KoufaxSandy
Student: ID = 89300  Name = KoufaxSandy
Student: ID = 67890  Name = RizzutoPhil
Student: ID = 54321  Name = BerraYogi
Student: ID = 67890  Name = MantleMickey
Student: ID = 99887  Name = MarisRoger
Student: ID = 62109  Name = FordWhitey
 */
