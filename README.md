## How To run

- Clone the repo
- Let intellij set up the project for you
- run BobabuddyApplication in src/main/java
- Spring boot will initialize a web server on localhost, which you can access with curl through
  url: http://127.0.0.1:8080/<api endpoints>
- Alternatively, make api calls by opening the ItemController class in com.boba.bobabuddy.infrastructure.controller in
  intellij and click on the green icons on the left side of each method definitions.
- some sample api calls:

creating a store:

```
POST http://localhost:8080/api/store/
Content-Type: application/json

{
  "name": "bob's bubble tea",
  "location": "St.George"
}
```

creating an item and add it to a store

```
POST http://localhost:8080/api/store/<storeId>/item/
Content-Type: application/json

{
  "price": 17,
  "name": "milk tea"
}
```

creating a user

```
POST http://localhost:8080/api/user/
Content-Type: application/json

{
  "email": "yeye@gmail.com",
  "name": "yeye",
  "password": "yeye123"
}
```

creating a rating and add association

```
POST http://localhost:8080/api/store/<storeId>/rating/?createdBy=yeye@gmail.com
Content-Type: application/json

{
  "rating": 1
}

###
POST http://localhost:8080/api/item/<itemId>/rating/?createdBy=yeye@gmail.com
Content-Type: application/json

{
  "rating": 0
}
```

search item by partial name match

```
GET http://localhost:8080/api/item/?name-contain=milk
```

search item by price leq & sort

```
GET http://localhost:8080/api/item/?price-leq=15&sorted=true

```

more example & full documentation to come

### where to start

Have a look at the refactored entity classes and try to understand the interaction between entity classes, the FindItem
usecase, ItemJpaRepository, and ItemController.

These are arguably the most informative. Once you have a rough idea of how these classes interact you can start writing
your own usecases.
