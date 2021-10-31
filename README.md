## How To run

- Clone the repo
- Checkout to branch phase1
- Let intellij set up the project for you
- run BobabuddyApplication in src/main/java
- Spring boot will initialize a web server on localhost, which you can access with curl through url: http://127.0.0.1:8080/<api endpoints>
- Alternatively, make api calls by opening the ItemController class in com.boba.bobabuddy.infrastructure.controller in 
intellij and click on the green icons on the left side of each method definitions.
- some sample api calls:

```
POST http://localhost:8080/api/item/
Content-Type: application/json

{
  "price": 19,
  "store": {
    "name": "bob",
    "location": "St. George"
  },
  "name": "milk tea"
}
```
```
GET http://localhost:8080/api/item/name-contain
Content-Type: application/json

{
  "name": "tea"
}
```

### where to start
Have a look at the refactored entity classes and try to understand the interaction between entity classes, the FindItem usecase, ItemJpaRepository, and ItemController.

These are arguably the most informative. Once you have a rough idea of how these classes interact you can start writing your own usecases.
