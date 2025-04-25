#include <iostream>
#include <string>
#include <iomanip>

class Date
{
public:
    int month;
    int day;
    int year;
    Date(int month, int day, int year) : month(month), day(day), year(year) {}
};


class Employee
{
protected:
    std::string _name;
    int _num;
    Date _hireDate;

public:
    Employee(const std::string& name, int num, const Date& hireDate) \
    : _name(name), _num(num), _hireDate(hireDate) {}

    explicit Employee(const std::string& name = "", int num = -1, int month = -1, int day = -1, int year = -1)
    : _name(name), _num(num), _hireDate(Date(month, day, year)) {}

    std::string getName() const { return this->_name; }
    int getNum() const { return this->_num; }
    Date getHireDate() const { return this->_hireDate; }

    void setName(const std::string& name) { this->_name = name; }
    void setNum(int num) { this->_num = num; }
    void setHireMonth(int month) { this->_hireDate.month = month; }
    void setHireDay(int day) { this->_hireDate.day = day; }
    void setHireYear(int year) { this->_hireDate.year = year; }
};


class ShiftSupervisor: public Employee
{
private:
    float _salary;
    float _bonus;

public:
    ShiftSupervisor(const std::string& name, int num, const Date& hireDate, float salary, float bonus)
    : Employee::Employee(name, num, hireDate), _salary(salary), _bonus(bonus) {}

    explicit ShiftSupervisor(const std::string& name, int num, int month, int day, int year, float salary, float bonus)
    : Employee::Employee(name, num, month, day, year), _salary(salary), _bonus(bonus) {}

    float getSalary() const { return this->_salary; }
    float getBonus() const { return this->_bonus; }

    void setSalary(float salary) { this->_salary = salary; }
    void setBonus(float bonus) { this->_bonus; }
};


int main()
{

    ShiftSupervisor supervisor("John Doe", 859, Date(7, 24, 2014), 75000.0f, 15000.0f);

    std::cout << std::setprecision(2) << std::fixed
    << "Name: " << supervisor.getName()
    << "\nEmployee number: " << supervisor.getNum()
    << "\nHire Date: " << supervisor.getHireDate().month << "/" << supervisor.getHireDate().day << "/" << supervisor.getHireDate().year
    << "\nAnnual Salary: $" << supervisor.getSalary()
    << "\nAnnual Production Bonus: $" << supervisor.getBonus() << std::endl;

    return 0;

}
