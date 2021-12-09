App Demo: https://youtube.com/playlist?list=PLECF-cwue5-sygEJ1i_h4csWJfxgqw_er


## Specification: Product Comparison System

Our application is a product comparison system that is designed for students from University of Toronto. The purpose of this program is to keep track of prices and ratings for bubble teas, sort them based on a user supplied parameter (price, distance or rating). The item list will then be displayed via each individual item’s store. With this, the user can find their desired bubble tea, along with its corresponding store location and pricing.

Within the program, there are four main types of object: item, store, user, and rating. Each item in the program is associated with a name, an id, a price, a category, an image url, the shop in which it is being sold, a list of ratings of this item, and its average rating. Each store is associated with a store name, an id, an image url, a list of items (a menu), a location, a list of ratings and a corresponding average rating. Each user is associated with a name, an id, an email, a password, a set of ratings that the user has made, and the type of privilege the user has.

Apart from using the application for its intended purpose (sorting items), admin users accessing the program also have the ability to create items, stores, and ratings. Admins may also delete or modify these entities at will.

From phase 1 -> phase 2

In phase 1, we have fully implemented all the entity classes(Item, Store, User, Rating) along with their use cases and their controllers. Moreover we have implemented our database to hold the information of each entity class. With the program, the user can create a store, item, user or a rating. The entity that is created by the user of the program will be stored in the corresponding database of the program.

In phase 2, we expanded upon our program to add support for many new features. This includes a role based authentication system and categories and profile image. Furthermore, our rating system can now be fully accessible from the frontend app. The user profile and the admin panel have also been partially implemented. We also did some refactoring and code cleanup to make sure that our code base is more clear in structure, less complex and more readable.

## Design Decisions

A big design decision we made for phase 1 is to implement our program as a web application. This means that instead of a single program responsible for everything, we would implement a backend server for handling data and a frontend UI that interacts with the user. We opted for using Spring framework to implement our backend as a REST API server, and Flutter for deploying frontend apps. We think that such a model is well suited for our program as a rating + price tracking app, and it demonstrates well the separation of layers in terms of a clean architecture design.


For authentication we opted to use Firebase Auth to store password and generate session token for us instead of handling these on our own backend app. We believed that rather than implement the core authentication functionalities ourselves, using matured authentication solutions like Firebase Auth will offer us much stronger security by decoupling password storage and token issuing responsibility from our own resource server.


We chose to store images as url and use Firebase storage to upload our images and get permanent url. This way we can easily decouple the responsibility of storing large content from our data server, improving IO performance. In the future we can also easily hook up Contend Delivery Network so that clients can access these image resource at very low latency even when our data server is far away.

## Clean Architecture Properties

We believe that our project adheres well to the clean architecture design, in fact, our project is packaged according to the clean architecture layers. Our entity classes are within the `core.domain` package, our use case classes in `core.service` and `core.data` package, adapters in `framework.controller` and `framework.adapter` package, and framework classes in `framework.config`, external dependencies, and our main application.

  

Elaborating further, the classes in our entity layer do not depend on anything from the outer layer apart from the Java Persistence API. Our use cases also only depend on Entities and interfaces from the outer layer. The packaging of our project shows clear separation of layers and this can also be observed from the import statements from each of our classes.

  
  

## SOLID properties

-   Single responsibility principle:

	-   In our program, we’ve split the responsibilities of each use case in such a way that every class handles only one (e.g. the item use case is separated into the following classes: createItem, findItem, updateItem, removeItem). This is a far more efficient design than having one use case class overloaded with several responsibilities.
    
	-   Since each class has a single responsibility we are able to change the classes freely without worrying about the effects of the change on other classes.
    

-   Open/closed principle:

	-   Throughout programming, we have adhered to the open/closed principle closely, and this principle has allowed us to expand our program freely. For instance, in phase 1, we only implemented the use case for Item entity. For phase 2, we were able to expand the use case by adding more functionalities to the use case. Moreover, while developing our program, we were able add new features to each use case whenever it is needed. This also shows that we have adhered to the open/closed principle closely.
    

-   Substitution principle:

    - One aspect that clearly demonstrate Substitution principle in our program is the inheritance relationship between Store, Item, and RatableObject. 
    Entities item and store extend ratable objects, where they both contain an ID, name, and average rating. Both classes add additional variables and methods to the abstract class, and substituting parent classes with child classes does not cause our program to break. 
    It is thus clear that our program adheres well to the Liskov Substitution principle


-   Interface segregation principle:

    - Our program adheres to the Interface Segregation Principle by making sure each interface contains as litter methods as possible.
    Classes that require multiple interfaces can simply implement all of them, while classes that only need some are not forced to implement the others.
    This can be clearly seen from our Input boundary design: each usecase offers one interface only.
    As such, no classes in our program are forced to implement unnecessary methods.
    

-   Dependency inversion principle:

    - Our program heavily utilize dependency injection, and this can be clearly observed from our use case (service) classes and our controller classes.
    For instance, during construction of our controller classes, many use case instances are passed in to support the controller's function. These dependencies are thus "injected" to our controller classes.
    Further, use cases instances are injected as their interface type. This means the controller no longer depends on the implementation of these use case services but instead on the contract of service formed by the interface.
    Thus, the inversion of dependency is achieved. 
    

## Packaging Strategy

While developing the program, we found that the most efficient way to package our code is to use packaging by layers. Because our program is very big, and because we do have lots of classes, we want to make sure that we can organize our files efficiently so that it is easy for us to understand the structure of the program, easy to find the files, and also it is easy to distinguish between each layer. For our program, each root package contains classes and packages from the same layer. This packaging strategy has allowed us to add classes easily, because the layers are well separated.

## Design patterns

-   We applied Dependency injection throughout our project, in part due to the nature of a Spring project. In a Spring project all classes are handled as beans that can be injected, or autowired, to other beans that have matching constructor types. This means Spring will automatically inject dependency as needed without explicit configuration.
    
-   We also applied the Model-View-Controller pattern through the usage of Spring Web MVC. This dependency allows Spring framework to generate implementations that handle a lot of the controller work for us: listening to web requests, parsing JSON to entity classes. The actual controller methods invoked by request mapping will then call the appropriate service (Model) to perform tasks. Then, the return value of these controller classes are again handled by Spring MVC which serializes them to a JSON and passes it back to the view, which is either a webpage or frontend UI.
    
-   Further, we applied Data Access Object and Data Transfer Object patterns for our data IO needs. The DAO pattern can be seen in the core.data.dao package, and it acts as an interface for use case classes to make CRUD operations on our database implementation. The DTO pattern can be seen in the core.data.dto package, and it is used to deliver data between the frontend and our backend controllers. The DTO classes are also designed to be much easier to serialize, since data carried by DTO classes need to be sent and received as JSON.
    
-   We implemented builder pattern back into our entity classes for a more readable construction of entity objects during create use cases. The builder classes are auto-generated by the library lombok, which is used to generate many useful boilerplate codes on compile time.
    

  


## Progress Report
  
#### What had worked well

Throughout the process of developing our program there are two things that worked out well for us, and they are listed below:

-   Spring framework allowed us to implement many functionalities, such as data persistence, without having to write too much code ourselves.
    
-   REST api allows frontend development to be completely separated for the backend. Developing a frontend app required little to no knowledge of the implementation details on the backend server.
    

  

#### Group Tasks

Phase 1 tasks:

-   Group 1 (Arzu, Angela): implemented store use case and its controller (along with related testing)
    
-   Group 2 (Leo, Ricky): implemented rating use case and its controller (along with related testing)
    
-   Group 3 (Mason, Arup): implemented user use case and its controller (along with related testing)
    

  

Phase 2 tasks:

-   Group 1 (Arzu, Angela): Add support for pictures and categories (along with related testing)
    

	-   The implementation of categories involved adding new item variables, new sort methods of items, as well as support for adding/editing categories via new entities, use cases, and a category controller. (branch: categories)
    
	-   The implementation of entity pictures requires new functionality to related entities: item, store, user (updating entity pictures, storing image URLs). (branch: pics_implementation)
    

-   Group 2 (Leo, Ricky): added support for firebase authentication and role-based access control to our backend server.
    

	-   Only logged in users from the frontend can access our app, and only admin users can perform privileged requests such as creating and deleting items and stores.
    
	-   We also implemented ownership-based control, so that a normal user can access their own user profile but not anyone else’s.
    

-   Group 3 (Mason, Arup): Added support for Map(along with related testing), rating functionality, and front end admin features
    

	-   Added google maps support with the maps api to allow for directions to a given store.
    
	-   Implemented the rating functionality of items to allow for users to rate the items using a thumbs up or down interface on the front end, which is recorded in our backend.
    
	-   Implemented an admin panel on the front end which allows for admin users to create and delete stores when needed.
    

  

## Pull requests for each member for phase 2

Leo: 
- [Refactoring of the Project Structure by Maddobun · Pull Request #34 · CSC207-UofT/course-project-professional-speedrunners (github.com)](https://github.com/CSC207-UofT/course-project-professional-speedrunners/pull/34)
- This pull request contained many quality of life changes, including lombok support, reduced code complexity, better DTO mapping, sort, and laid ground works for authentication support. 
  

Mason: 
- [https://github.com/CSC207-UofT/course-project-professional-speedrunners/pull/28](https://github.com/CSC207-UofT/course-project-professional-speedrunners/pull/28)
- (Pull request made by Leo but creation and changes were made here because of initial basing issues [https://github.com/PondsFargo/BobaBuddyUI](https://github.com/PondsFargo/BobaBuddyUI) )

  

-   I think this demonstrates a significant contribution to the project as it laid the foundation of the user interface and allowed for key features of our program to be demonstrated in a practical, and user friendly way.
    

  

Ricky: 
- [https://github.com/CSC207-UofT/course-project-professional-speedrunners/pull/35](https://github.com/CSC207-UofT/course-project-professional-speedrunners/pull/35)

- I think this demonstrates a significant contribution because it represents a large part of the authentication and access implemented in phase 2.

  

Angela & Arzu: 
- [https://github.com/CSC207-UofT/course-project-professional-speedrunners/pull/39](https://github.com/CSC207-UofT/course-project-professional-speedrunners/pull/39)

- This is where we implemented the categories features - separate new entity (Items contain multiple categories which can be used to find/sort items) (Worked on these features as a group)

- This pull request demonstrates a significant contribution because we have created a completely new entity (category) and have modified the Item entity according to our new implementation. Moreover, we have added image support for Item, Store and User entities so that any user can upload an URL to change the image of these entities. This is a brand new feature which was not supported in neither phase 0 nor phase 1.
    

  
Arup: 
- https://github.com/CSC207-UofT/course-project-professional-speedrunners/pull/40
- Added the rating system in the front end for phase 2 which allows the user to be able to finally interact with the rating entities and its use cases.
- [https://github.com/CSC207-UofT/course-project-professional-speedrunners/commit/c6a5a4bb95be3554d482cab394bf706592710077](https://github.com/CSC207-UofT/course-project-professional-speedrunners/commit/c6a5a4bb95be3554d482cab394bf706592710077) 
- If you need an example of me working in the backend, this was a commit I made in phase 1 which is when I added majority of the user entity use cases and user entity controller.


# Accessibility Report

- Equitable Use:
	-   Our application is useful and marketable to people with diverse abilities. While implementing the app we did not segrate any user, anyone can create an account to access the service we have implemented. Moreover, every user’s information is secure within our application.
	-   However, there is room to improve in order to make our application more equitable. For instance, we can change the design of the UI, so that the design of the app is appealing to a wider range of users.

- Flexibility in Use:
	-  In our application we have implemented different categories for bubble tea. With this wide range of choices, users can indicate their preference while trying to find their desired bubble tea on our application. Therefore we have accommodated a wide range of individual preferences while designing.

	- In the future, we could change our UI design so that it accommodates right and left handed access and use. Moreover, we can change the design of our UI in order to accommodate individuals with a wide range of abilities to use our application comfortably. For instance, we can change our text font so that individuals with visual impairment can use our application without having to struggle.

- Simple and Intuitive Use:
	- Our application has been designed to be quite straight forward in function, using simple terminology as well as any standard formatting used by other popular apps (the knowledge is transferable and intuitive). Additionally, we’ve assured that all presented data will follow the same format/template, where also we’ve ordered the information based on its importance (more easily visible -> more important)

- Perceptible Information:
	- We’ve made sure to design our GUI so that users can tell apart bits of information being presented to them. We’ve done this by using variations in text colours/fonts (e.g. state of store(open or closed) in green, the name of the store is in a bigger font in black-easily readable). Additionally, we’ve made sure that essential information is the most visible. For example, the opening hours for stores users may want to visit is highlighted in red as this is an important piece of information that may allow users to determine whether or not they want to visit this store.

- Tolerance for Error:
	- One feature we added which adheres to this principle is a confirmation dialog for admin users when a delete store action is initiated. Since this action is one that cannot be undone we also force admin users to re login each time they delete a store to discourage multiple deletes. We did this because deleting a store is an action that is very rarely needed and permanent so the utmost caution is needed when performing such a task.

- Low Physical Effort:
	- Since the main feature of our application is to allow users to quickly search for a drink to find the cheapest option, we have the main layout designed around that idea. Once logged in the user is able to immediately search for a drink they’re looking for and the results are automatically sorted by cheapest first. This ensures the user does not need to repeatedly scroll to find the cheapest item in the search. Another feature added is the ability to navigate to the store in one click. We have implemented google maps to limit the amount of effort put into navigating to a store.

- Size and Space for Approach and Use:
	-    Our UI elements currently do not adhere to this principle, since some of the widgets on top of the screen can be hard to reach in a one-handed phone usage.
	- In the future, we could implement changes to the UI layout design where, either users can customize the layout themselves, or we can design a better layout such that frequently interacted UI elements not only stand out more visually, but are also closer to the bottom of the screen for ease of access.

## Target Market

The main purpose of our program is to help students that are in University of Toronto to find affordable bubble teas around the campus. Due to this, our main target market will be students who go to UofT. As well, this application may be suitable for general bubble tea enthusiasts who live in the same area, since we only considered the bubble tea stores that are around our campus.

## Lost Demographic

Since our target demographic is quite specific, those that are less likely to use our program do so mainly because of lack of interest. The more obvious demographic would be those who don’t like bubble tea, where the lack of interest is expected. Further, since the app is designed to be used within University of Toronto only, the general public will be less likely to use it because it does not contain relevant information outside of UofT campus. There may also be boundaries in the usage of our app which may prevent those with disabilities from using it to its fullest. For example, this app is currently not suitable for the blind community given that there are no ALT texts for our images, where we weren’t able to take into account the use of text-to-speech programs when considering our app layout due to time constraints (This would be a next step).

