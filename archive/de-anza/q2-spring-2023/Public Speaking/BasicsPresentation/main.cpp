#include <iostream>


class RollingAverage
{
public:
    explicit RollingAverage(size_t size)
    {
        this->_values = new int*[size];
        for (int i = 0; i < size; i++)
        {
            this->_values[i] = new int;
            *this->_values[i] = -1;
        }

        *this->_size = size;
        this->_iter = this->_values[0];
    }

    ~RollingAverage()
    {
        for (int i = 0; i < *this->_size; i++) { delete this->_values[i]; }
        delete[] this->_values;
        delete this->_size;
    }

    inline size_t size() const { return *this->_size; }
    inline int at(unsigned int index) const { return *this->_values[index]; }

    void push(unsigned int value)
    {
        *this->_iter = (int)value;

        if (this->_iter == this->_values[this->size() - 1])
        {
            this->_iter = this->_values[0];
        }
        else
        {
            this->_iter++;
        }
    }

    float average() const
    {
        int sum = 0;
        int numVals = 0;

        for (; numVals < this->size() && this->at(numVals) != -1; numVals++)
        {
            sum += this->at(numVals);
        }

        return (float)sum / (float)numVals;
    }

private:
    int** _values;
    size_t* _size = new size_t;
    int* _iter;
};


int main()
{
    RollingAverage avg(10);

    for (int i = 1; i < 51; i++)
    {
        avg.push(i);
    }

    std::cout << avg.average() << std::endl;

//    size_t* size = new size_t; *size = 10;
//    int** values = new int*[*size];

    return 0;
}