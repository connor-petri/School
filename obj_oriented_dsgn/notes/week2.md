# Week 2

---

## Overview
- Requirement Analysis
- O.O. Design
- Implementation
- Inputs and outputs of above stages

## Requirement Analysis
- Input: **Client Request**
    - Often non-technical people making request
- Output: **Requirement Spec.**
    - A technical document with agreed upon functional and non-functional requirements

### Two categories of requirement:
#### Functional Requirements
- concerned with functions and services to be carried out by the software
- Examining **use cases** will populate functional requirements
- i.e. Calander app must be able to add, remove, and edit events
#### Non-Functional Requirements
- concerned with the constraints under which the software must operate, such as response time and memory consumption
- i.e. Calandar app must run on a S.O.C. with 2 kb of RAM

### Use Cases
- Examines the interactions between user and system
- This informs designers which functional requirements are needed
- Variations and edge cases should be explored to create a complete use case

#### Example: Leave a Message
##### Original Case
- User: Dials phone number
- System: The voice mail system plays a prompt
- User: The caller types in the extension of the person they are trying to reach
- System: Speaks "you have reached the voice mailbox of xxxx..."
- User: Leaves a message
- User: Hangs up
- System: Saves recorded message to file system

##### Variation
- User: Dials
- System: Plays prompt
- User: hangs up
- System: Does not save message data

## Object Oriented Design Phase
- Goal: To identify classes/interfaces, their responsibilites, and relationships

### Tools:
#### Noun/Verb analysis
- Goal: To form an inital set of classes and responsibility
- **Nouns** are people, places, and things we need to write classes for
    - i.e. Mailbox, Event, Message
- **Verbs** are actions that need to be performed, which inform class responsibilities. Usually the member functions of a class
    - i.e. Save, Delete, Edit

##### Noun Analysis
- User and Role
    - Used to establish different users with different roles and permissions of a system
- Systems
    - Model a subsystem or the overall system being built
    - Used to initiate and terminate the system
- System interfaces and devices
    - These capture system resources and the interaction of the system
    - Display window, input reader, output file, etc
    - i.e. ```File``` class
- Foundational Classes
    - These are typically generic fundamental classes
    - At the beginning we should assume they exist

##### Responsibility/Verb Analysis
- A responsibility must belong to exactly one class
- A responsibility of a class should stay at one abstraction level

#### CRC
- Goal: Refine classes and responsibilities and define relationships
- Stands for Classes, Responsibilities, and Collaboration

### Identity vs State
- An objects **identity** comes from it's memory address
- An objects **state** comes from the content of it's member variables

#### Object Comparison
- ```==``` compares by memory address (are 2 variables pointing to the same obj)
- ```.equals()``` compares by content, or the member variables of the object

### Other Common Object Types
#### Agent Classes 
- Encapsulate commonly used operations within an application
- i.e. a class with all file IO operations

#### Events
- Holds all information related to a particular event in a system
- i.e. onMouseClick, onKeyPress

### Relationships
#### Is-a
- Class 1 *is a* Class 2
- **Inheritance** literally means the child object *is a* parent object, and can be cast as such
- **Interface** means the object impliments an interface, meaning it can do the things other classes that impliment the same interface can do
    - Remember that interfaces can extend from other interfaces
- *Implimentation Specific*
- UML Syntax:
![UML syntac for Inheritance](img/UML-Inheritance.png)

#### Has-a
- **Aggregation** is a weaker form of composition
- **Composition** literally means Object1 *has a* member variable of type Object2
- For our purposes, we will treat aggregation and composition the same
- Always an instance variable
    - Non user-defined instance variables are called attributes, and do not have a has-a relationship with the class
- UML Syntax:
![UML syntax for Composition/Aggregation](img/Aggregation.png)

#### Uses
- Object1 *uses* Object2
- Also called **Dependency**
- A temporary relationship where Object1 needs Object2 to perform a task
    - Return typs
    - Method parameters
- UML Syntax:
![UML syntax for Dependency](img/uses.png)
##### Optimized Version of Above Example
- Avoids parameter passing overhead
![UML syntax for Dependency Optimized](img/uses-aggregation.png)

#### Association
- Object1 *associates with* Object2
- A more permanent relationship than dependency, but not as strong as composition
- Bidirectional unless otherwise specified by arrows
- UML Syntax:
![UML syntax for Association](img/Association.png)
- If unidirectional, use arrows to indicate direction of association
![UML syntax for Unidirectional Association](img/association2.png)

### Use Case Study
- Use cases should be initiated by an actor (user or external system)
- Identify variations and edge cases
#### Use Case: Reach an Extension
| Step | User Action | System Response |
|-------|--------------|------------------|
| 1 | Dials phone number | |
| 2 | | The voice mail system plays a prompt |
| 3 | Types in extension of person trying to reach | |
| 4 | | Speaks "you have reached the voice mailbox of xxxx..." |

#### Use Case: Leave a Message
| Step | User Action | System Response |
|-------|--------------|------------------|
|1 | Carries out **Reach an Extension** | |
|2 | Leaves a message | |
|3| Hangs up | |
|4| | Saves recorded message to file system |

#### Use Case: Log In
| Step | User Action | System Response |
|-------|--------------|------------------|
|1 | Mailbox owner carries out **Reach an Extension** | |
|2 | Enters password | |
|3 | | Verifies password |
|4 | | Grants access to mailbox functions |

#### Use Case: Retrieve Messages
| Step | User Action | System Response |
|-------|--------------|------------------|
|1 | Mailbox owner carries out **Log In** | |
|2 | Requests to retrieve messages | |
|3 | | System plays back menu options |
|4 | Requests to hear current messages | |
|5 | | System plays back current messages |