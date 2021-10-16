# Rating:
- GetRatingId();
	- return the id corresponding to this rating
		- type String
- GetRaing()
	- return the rating (0 : Thumbs down, 1 : Thumbs up)
- GetRatingUser()
	- return the string ID corresponding to user that left the rating
		- type String
- UpdateRating(Int)
	- update the rating from a thumbs down to thumbs up or vice versa and return true if the update was successful, false otherwise


# User
- GetName():
	- return the name of this user
		- type String
- GetEmail():
	- return the email of this user
		- type String
- ChangeEmail(String):
	- update the email of this user and return true if successful, false otherwise
- GetUserId():
	- return the id of this user
		- type String
- GetRatings():
	- return the list of rating ids this user has made
		- type List of Strings
- AddRating(String):
	- add rating id to this users ratings and return true if successful, false otherwise
		

# BubbleTeaStore implements Ratable:
- GetName():
	- return name of the store
		- type String
- GetLocation():
	- return the location
		- type String
- UpdateLocation(String):
	- Update the location and return true if the update was successful, false otherwise

- GetStoreId():
	- return the id of this store
		- type String

# Ratable (interface)
- GetRatings():
	- return a list of rating ids
		- type List of Strings
- DeleteRating(String):
	- Delete given rating, using its id, and return true if successful, false otherwise
- AddRating(String):
	- add given rating, using its id, to ratings and return true if successful, false otherwise




