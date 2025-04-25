using namespace std;
#include <iostream>
#include <fstream>
#include <string>

/*
  The following code file is divided into 3 sections

	1. The interface and code for Class Student  (supplied)
	2. The interface and code for class StudentEsa  (The interface is supplied. HM1 is writing the code to implement this interface)
	3. The HM1 test program code (supplied)
*/

// 1.  ************************ class Student code included here  ***************************

class Student
{   // A VERY simple Student consisting only of the student's ID and Name
    // Both the interface and the code will be located here.

private:
    int sid; // Student ID
    string sname; // Full Name (Ex: KleinmanRon)

public:
    Student() { // Default constructor
        sid = 0; sname.clear();
    };
    Student(const Student& os) { // Copy constructor - overwrite internal variables
        sid = os.sid;  sname = os.sname;
    };
    Student(int id, string name) { // Initializer Constructor - set internal variables
        sid = id; sname = name;
    };
    ~Student() { // Default Destructor - clear string
        sname.clear();
    };


    //Getters
    string getName() const { return this->sname; };
    int getId() const { return this->sid; };
};

//      ********************** End class Student ***************************************


// 2. *********** Homework 1.  insert the code for the StudentEsa into the StudentEsa interface below *******



template <class T>
class ExtendableArray
{
public:
    // Used a default argument to combine the Default constructor and standard constructor.
    // Arrays of size 0 exist, so Extendable arrays of size 0 should be able to exist too.
    ExtendableArray(unsigned int size = 0)
    {
        this->arr = new T*[size];
        this->arrSize = size;
        this->numElements = 0;
    }

    ExtendableArray(const ExtendableArray &array) // Copy constructor
    {
        this->arr = new T*[array.getSize()];
        this->arrSize, this->numElements = array.getSize();
        for (int i = 0; i < array.getSize(); i++)
        {
            this->arr[i] = array.get(i);
        }
    }

    int getSize() { return (int)this->numElements; }

    T * get(unsigned int index)
    {
        if (index >= this->numElements) { return NULL; }
        return this->arr[index];
    }

    int set(T *ptr, unsigned int index)
    {
        if (index >= this->numElements) { return -1; }
        this->arr[index] = ptr;
        return (int)index;
    }

    int insert(T *ptr, unsigned int index)
    {
        if (index > this->numElements) { return -1; }

        if (++this->numElements > this->arrSize) { realloc(); }

        T *temp1 = NULL;
        T *temp2 = ptr;
        for (int i = index; i <= this->numElements; i++)
        {
            temp1 = this->arr[i];
            this->arr[i] = temp2;
            temp2 = temp1;
        }

        return (int)index;
    }

    int remove(unsigned int index)
    {
        if (index >= this->numElements) { return -1; }
        for (int i = index; i < this->numElements; i++)
        {
            this->arr[i] = this->arr[i + 1];
        }
        this->arr[this->numElements - 1] = NULL;
        this->numElements--;

        return (int)index;
    }

    int append(T *ptr)
    {
        return this->insert(ptr, this->numElements);
    }

    int prepend(T *ptr)
    {
        return this->insert(ptr, 0);
    }

protected:
    T **arr;
    unsigned int arrSize;
    unsigned int numElements;

    // when called, realloc() will create a new array of twice the size of the old one, and copy each element over.
    void realloc()
    {
        T **newArr = new T*[this->arrSize * 2];
        for (int i = 0; i < this->arrSize; i++)
        {
            newArr[i] = this->arr[i];
        }
        this->arr = newArr;
        this->arrSize *= 2;
    }
};


// **************************** End class StudentEsa ******************************


// 3.  ***********************  Start of StudentEsa Test Program Code  ************

int main()
{
    // Redirect Input
    string infileName; // Use if Input redirected
    string outfileName; // Use if output redirected
    FILE** input = NULL;  // Recovering Cin


    // Command File Record entries
    int ssize; // Size of extended array
    int nops; // # operations to perform

    char command; // Command (I,A,R,S)
    // Not all of the following are present in each command (Default given)
    int index; // Array Index (-1 default)
    int num;  // Student ID Number (-1 default)
    string name;  // Student Name (XXXX default)

    ExtendableArray<Student> *esa = NULL;   // Pointer to Enhanced Student Array
    Student sc; // A class to collect Students is generated

    int x=0; // Useful variables

    cout << "Use input file:  ";
    infileName = "../in11.txt";
//    cin >> infileName; // Get name of file containing input data
    cout << "Using input file " << infileName << endl << endl;


    // Create an input file stream to read supplied file
    std::ifstream inp;
    inp.open(infileName.c_str());
    if (!inp) {
        cerr << "Error: file " << infileName.c_str()<< "  could not be opened" << endl;
        exit(1);
    }

    // First line is array size and # commands to add, every subsequent line is one of:
    //  Append:  A / -1 / StudentID / Student Name
    //  Insert:  I / Index to Insert / StudentID / Student Name
    //  Remove:  R / Index to Remove / -1 / XXXX
    //  Store:   S / Index to Store (overwrite) /  new StudentID / new Student Name

    // Get Size of Extended Array and # of commands
    // ssize is size of extended array, nops is # commands

    inp >> ssize >> nops;
    cout << "Read Array size " << ssize << "  Read # commands " << nops << endl;

    Student* stud;    // Array to hold pointer of created student.
    esa = new ExtendableArray<Student>(ssize);  // Small Student Enhanced Array.  May have to be resized.


    for (int i = 0; i < nops; i++) {  // Process Commands
        //**************************************************************
        inp >> command;
        inp >> index;
        inp >> num;
        inp >> name;
        cout << "Command: " << command << "  " << index <<"  " << num << "  " << name << endl;

        // Process each command
        switch (command)
        { // Convert to command for Extended Array
            case 'A':  // Append
                stud = new Student(num, name);
                esa->append(stud);
                break;
            case 'I':  // Insert
                stud = new Student(num, name);
                esa->insert(stud, index);
                break;
            case 'R':  // Remove (delete)
                esa->remove(index);
                break;
            case 'S': // Store over existing Student
                stud = new Student(num, name);
                esa->set(stud, index);
                break;
            default:
                cout << "Illegal Command:  " << command << endl;
        }

    }
    // Print out Current contents of extended array
    cout << "-------" << endl << endl;

    x = esa->getSize(); // Get size of array (number of active elements
    for (int i = 0; i < x; i++)
    {  // Print each element
        stud = esa->get(i); // Get ptr to ith student in array
        cout << i << "  Student: ID = " << stud->getId() << "  Name = " << stud->getName() << endl;
    }

    return (0);
}

// **********************************  End of test code  *************************************
