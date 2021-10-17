Specification: Price Comparing System
=============

This is a price comparing system that is designed for students from University of Toronto. The purpose of this program is to keep track of prices and ratings for bubble teas, sort them based on a user supplied parameter (price, distance or rating), and present the store that is associated with the item on the map to the user. With this, the user can find their desired bubble tea with the store location and the price information. 

Within the program, there are three main entity classes, they are item, store, and user. Each item in the program is associated with a name, an id, a price, the shop in which it is being sold and a list of ratings made to this item by the users. Each store is associated with a store name, an id, a list of item as menu, a location and a list of ratings. Each user is associated with a name, an id, an email, and a password.

In this program, there are three different types of users, they are logged in users, logged out users and admin. Users can log into the system with their username and password. Every user can get a sorted list of bubble teas on the map. However, only the logged in users can leave a rating (0 for thumbs down, 1 for thumbs up) or a review on the item or the store. Admins of the program can create stores, delete stores, modify the menu that is associated with the store, and update the price of an item.



