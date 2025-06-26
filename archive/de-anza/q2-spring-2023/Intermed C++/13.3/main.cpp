#include <iostream>

class Employee
{

private:
    std::string _name;
    int _idNumber;
    std::string _department;
    std::string _title;

public:
    Employee(const std::string& name, int id, const std::string& department, const std::string& title)
    : _name(name), _idNumber(id), _department(department), _title(title) {}

    Employee(const std::string& name, int id)
    : _name(name), _idNumber(id), _department(""), _title("") {}

    Employee()
    : _name(""), _idNumber(0), _department(""), _title("") {}

    std::string getName() const { return this->_name; }
    int getId() const { return this->_idNumber; }
    std::string getDepartment() const { return this->_department; }
    std::string getTitle() const { return this->_title; }

    void setName(const std::string& name) { this->_name = name; }
    void setId(int id) { this->_idNumber = id; }
    void setDepartment(const std::string& department) { this->_department = department; }
    void setTitle(const std::string& title) { this->_title = title; }


};

int main()
{

    Employee* employees[3] = { new Employee("Paul Rogers", 12345, "Accounting", "Vice President"),
                               new Employee("John Reid", 34567),
                               new Employee };

   employees[1]->setDepartment("IT");
   employees[1]->setTitle("Programmer");

   employees[2]->setName("Peter Meyers");
   employees[2]->setId(47889);
   employees[2]->setDepartment("Manufacturing");
   employees[2]->setTitle("Engineer");

   for (Employee* employee : employees)
   {
       std::cout    << "Name: " << employee->getName()
                    << "\nID Number: " << employee->getId()
                    << "\nDepartment: " << employee->getDepartment()
                    << "\nPosition: " << employee->getTitle() << "\n\n";
   }

   return 0;

}
