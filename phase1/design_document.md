# Specification: Product Comparison System

Our application is a product comparison system that is designed for students from University of Toronto. The purpose of this program is to keep track of prices and ratings for bubble teas, sort them based on a user supplied parameter (price, distance or rating). The item list will then be displayed via each individual item’s store. With this, the user can find their desired bubble tea, along with its corresponding store location and pricing.

Within the program, there are four main entity classes:  item, store, user, and rating. Each item in the program is associated with a name, an id, a price, the shop in which it is being sold,  a list of ratings of this item, and its average rating.  Each store is associated with a store name, an id, a list of items (a menu), a location, a list of ratings and a corresponding average rating. Each user is associated with a name, an id, an email, a password, and a set of ratings that the user has made.

Apart from using the application for its intended purpose (sorting items), users accessing the program also have the ability to create items, stores, user accounts, and ratings. Users may also delete or modify these entities at will.

## From phase 0 -> phase 1

In phase 0 we only implemented one entity class(Item) and its related use case.  The program allowed users to store a new item, update or delete an existing item. It also allowed the user to find all the items in the system or find the item using its unique ID. 

In phase 1, we have fully implemented all the entity classes(Item, Store, User, Rating) along with their use cases and their controllers. Moreover we have implemented our database to hold the information of each entity class. Now with the program, the user can create a store, item, user or a rating. The entity that is created by the user of the program will be stored in the corresponding database of the program.  

Moreover, we have implemented some part of our GUI, where the frontend GUI is able to interact with our backend. However, we have not yet finished the implementation, therefore we will continue to work on it for phase 2. 

Lastly, we have implemented the sorting feature for finding items by price and average rating, and for finding stores with average rating. 

# Design Decisions
A big design decision we made for phase 1 is to implement our program as a web application. This means that instead of a single program responsible for everything, we would implement a backend server for handling data and a frontend UI that interacts with the user. We opted for using Spring framework to implement our backend as a REST API server, and Flutter for deploying frontend apps. We think that such a model is well suited for our program as a rating + price tracking app, and it demonstrates well the separation of layers in terms of a clean architecture design.

# Clean Architecture Properties
We believe that our project adheres well to the clean architecture design. To start, the classes in our entity layer do not depend on anything from the outer layer apart from the Java Persistence API. Our use cases also only depend on Entities and interfaces from the outer layer. The packaging of our project shows clear separation of layers and this can also be observed from the import statements from each of our classes.

However there are some clean architecture violations we plan to fix in the future. One of which is that our controller classes use the entity classes directly as the Data Transfer Object. This is not desirable because entity classes are difficult to serialize and custom serialization parameters need to be set directly within the entity class. Further, this also exposes the entity classes directly to the outside world, which can be a security risk. 

Not using DTO also forced us to write custom deserializers which required dependency from the persistence layer. This made us unable to test controller classes in isolation and increased code complexity.

# SOLID properties
### Single responsibility principle: 
- In our program, we’ve split the responsibilities of each use case in such a way that every class handles only one (e.g. the item use case is separated into the following classes: createItem, findItem, updateItem, removeItem). This is a far more efficient design than having one use case class overloaded with several responsibilities.
- Since each class has a single responsibility we are able to change the classes freely without worrying about the effects of the change on other classes. 

### Open/closed principle:
- Throughout programming, we have adhered to the open/closed principle closely, and this principle has allowed us to expand our program freely. For instance, in phase 0, we only implemented the use case for Item entity. For phase 1, we were able to expand the use case by adding  more functionalities to the use case.  Moreover, while developing our program, we were able add new features to each use case whenever it is needed. This also shows that we have adhered to the open/closed principle closely. 

### Substitution principle:
- Entities item and store extend ratable objects, where they both contain an ID, name, and average rating. Both classes add additional variables and methods to the abstract class, implementing the substitution principle. 

### Interface segregation principle: 
- All interfaces are implemented in full; classes that extend them utilize all abstract methods in their interface. Then, no classes in our program are forced to implement necessary methods.

### Dependency inversion principle:
- Our program depends on the dependency injection.  For instance, for each use case there are ports available for them. These ports are responsible for the inversion. For example, use case creatitem, which is a high level class, does not depend on the controller, which is a  low level class, and JPA repo. By adding an interface, ICreateItem, we only allowed the high class to depend on the interface, so that the use case does not have to worry about the design of our controller and JPA repo. 

# Packaging Strategy
While developing the program, we found that the most efficient way to package our code is to use packaging by layers. Because our program is very big, and because we do have lots of classes, we want to make sure that we can organize our files efficiently so that it is easy for us to understand the structure of the program, easy to find the files, and also it is easy to distinguish between each layer. For our program, each root package contains classes and packages from the same layer. This packaging strategy has allowed us to add classes easily, because the layers are well separated.

# Design patterns
- We applied Dependency injection throughout our project, in part due to the nature of a Spring project. In a Spring project all classes are handled as beans that can be injected to other beans that have matching constructor types. This means Spring will automatically inject dependency as needed without explicit configuration.
- We also applied the Model-View-Controller pattern through the usage of Spring Web MVC. This dependency allows Spring framework to generate implementations that handle a lot of the controller work for us: listening to web requests, parsing JSON to entity classes. The actual controller methods invoked by request mapping will then call the appropriate service (Model) to perform tasks. Then, the return value of these controller classes are again handled by Spring MVC which serializes them to a JSON and pass it back to the view, which is either a webpage or frontend UI.  

# Progress Report
### Project Questions
- Currently we do not have any questions. But we will ask for TA's help whenever it is needed. 

### What had worked well
Throughout the process of developing our program there are two things that worked out well for us and they are listed below: 
Spring framework allowed us to implement many functionalities, such as data persistence, without having to write too much code ourselves.
REST api allowed frontend development to be completely separated for the backend. Developing frontend app required little to no knowledge on the implementation details on the backend server.

### Group Tasks
- Group 1 (Arzu, Angela): implemented store use case and its controller (along with related testing)
- Group 2 (Leo, Ricky): implemented rating  use case and its controller (along with related testing)
- Group 3 (Mason, Arup):  implemented user use case and its controller (along with related testing)
- GUI implementation: Mason

### What to work on next
During phase 1, we have split into 3 groups based on the use case and the controllers we have. However we realized that when we splitted into groups based on the use cases we have, we were primarily focusing on the backend design, and neglected the frontend design. Because of this, Mason had to work almost entirely by himself for the frontend. Thus, in phase 1 we were not able to finish our UI. 

While considering what we have done wrong in phase 1, we decided to come up with a better way to work together, so that we can work efficiently not only on the backend, but also on the frontend. Therefore, for phase 2, we will be splitting into 2 groups of 3 to mature the program structure. We have not yet decided the members of group 1 and group 2, but we have come up with the tasks for each group. Below are the primary focuses for each group

**Group 1:**
Focus on backend design.
Add more test cases for each use case and controllers, make sure they are working properly without error. 
Make sure our design adheres to SOLID principles and clean architecture. 

**Group 2:**
Focus on frontend design.
Finish UI.
Make sure the UI is working properly such that it can interact with the backend without error.  
