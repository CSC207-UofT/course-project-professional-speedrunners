Specification:
=============
Our application is a price comparison program designed for students from the University of Toronto. The program can be used to sort bubble tea (specific to the type, e.g. milk tea) sold around campus by either price, location, or rating, where a sorted list of the bubble tea shops containing these items will be returned. Different users of the program have varying levels of privilege. For example, users with registered accounts can rate an item or a store, and users with admin privileges can update store items or information regarding the store itself. Our specifications document goes into more detail about these functionalities. 

CRC Model:
=============
We constructed a CRC model for this program that illustrates the relationships between entity classes, use cases, controllers and interfaces. The entity classes include Item, Store, User, and RatingPoints. Use case interactor classes such as ItemFacade, StoreFacade and UserFacade can create entities, remove them, modify their mutable fields, and most importantly sort them in a specified order. Use cases communicate with controllers, presenters, and databases through the use of interfaces. This ensures that dependency rules of clean architecture are not violated. In the adapter layers are the generic and specific controllers, presenters, and view interface. Controllers house logics that decide what use case method to invoke based on inputs made by the user. These inputs are collected through a view object, which acts as the user interface. The presenter class receives output from the use case and reformats the data in such a way that is readable by the view object. Lastly, the main method is where everything is initialized. 

Scenario Walk-through:
=============
A Uoft student who is in dire need of bubble tea opens up the app Boba Buddy in order to find their desired bubble tea at a reasonable price. When the student indicates that they want to sort bubble tea by price, their request will be sent to GenericController, which delegates the task to FindItemController. FindItemController will then call findItem and use the Sort use case to sort all the bubble tea that is sold near the campus by their prices. When the sorted list of bubble tea is presented to the student, they will then select their desired item. The item’s store location will be retrieved using the findStore class. Then, the information regarding the store will be presented to the student. If the student wishes to update the price of any item, UpdateItem will be evoked by the sub controller. The student will then be informed whether their entry was successful or not. After drinking the bubble tea, if the student wants to rate their drink or the store itself, their input will invoke a sub controller, which will add a RatingPoint to the Database. It links the user to the review and the review to the store or the item. Afterwards, the student will be informed of whether or not the action has been completed successfully. 

Skeleton Program:
=============
The skeleton program of this project has only implemented one entity class (Item) and its related use cases. When the user runs the program through main, the program will ask the user to select an operation. There are three options that are currently supported by the program, which are to create an item, find all the items in the program and sort them by price, or remove an item that is already in the program. If the user chooses to create an item, they will have to enter the name, price, and storeID associated with the item. Then once the item is successfully created, information about the item will be returned.  

If the user wishes to find all the items in the program by sorting them with their price, then the user can simply select this operation (option 2: findALL + Sort by price). Following the user input, a list of items sorted by their price will be returned. The items in the list will be listed with their name, price, and unique ID.  

Alternatively, if the user wishes to remove an Item already added into the system, they would need to supply the itemID corresponding to the item. This can be obtained by using the findAll operation. If an Item with a matching ID is found in the system, it will be deleted from the system and the string representation of the deleted item will be printed in the console. On the other hand, if the system cannot find an item with a matching ID, then the program will indicate that it has failed to remove the item from the system. 

What has worked well:
=============
While developing the project, there were numerous things that have worked well. First of all, the layers in our program are well separated, following the rules of clean architecture. For instance, entity classes do not directly interact with controller classes and vice versa. Thus, entity and use case classes can be easily tested using mocks without worrying about the details in the outer layers. Moreover, our design allows us to expand on the outer layers freely since the layers of our program are clearly separated meaning changes to the outer layers do not affect the entity classes. 

Current tasks:
=============
During phase 0 of the project we worked on the CRC model and brainstormed the design choices of our project together. After consolidating our CRC model together, we split up the remaining tasks. Leo was responsible for constructing the skeleton project with Ricky’s and Arup’s help. Angela, Leo and Mason were responsible for writing the unit tests for the skeleton project. Mason was also responsible for writing the scenario walk-through. Arzu was in charge of the specification and the progress report. Although we split up the remaining tasks, we helped each other whenever it was needed.  

Future tasks:
=============
After working together for phase 0, our group has decided to split into groups of 3 for future tasks to increase efficiency. However, we are still in the process of deciding the groups but have come up with the main tasks for future stages. Tasks will be updated throughout the process of programming. 
- Need to implement all the use cases, such as ratings, ability to sort, etc.
- Need to update user interface controls.
- Need more comprehensive testing, especially for edge cases.
- Need to implement GUI.   

Questions for the TA:
=============
At our current state we do not have many questions to ask our TA, however throughout the process of development we will check in with our TA regularly to get her feedback in order to perfect our design. Our questions are listed below:
- In our program we need to implement a map that can show the users where each bubble tea store is located. Are there any suggestions that you can give us for implementing the map using our current knowledge? What are the sources that we can use for the implementation? 
- When creating our app UI are we limited to using a particular framework or can we use whichever we like?
