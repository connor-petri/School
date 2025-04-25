#include <iostream>
#include <iomanip>

class Employee
{
private:
    int _id;
    std::string _name;
    float _payRate;
public:
    Employee(int id, const std::string& name, float payRate)
    : _id(id), _name(name), _payRate(payRate) {}

    friend std::ostream& operator<<(std::ostream& out, const Employee& e)
    {
        out << std::fixed << std::setprecision(2);
        out << "ID#: " << e._id << " Name: " << e._name << " Pay rate: $" << e._payRate;
        return out;
    }
};

template <typename T>
void stars(T in)
{
    std::cout << "**********" << in << "**********\n";
}

int main()
{
    Employee e(8754, "John Doe", 25.50f);
    stars(e);
    return 0;
}
