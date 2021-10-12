# Rating:
- GetRatingId();
	- return the id corresponding to this rating
		- type String
- GetRaing()
	- return the rating (0 : Thumbs down, 1 : Thumbs up)
- GetRatingUser()
	- return the string ID corresponding to user that left the rating
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
	- return the list of ratings this user has made
		- type Rating
- AddRating(Rating):
	- add rating to this users ratings and return true if successful, false otherwise
		

# BubbleTeaStore implements Ratable:
- GetName():
	- return name of the store
- GetLocation():
	- return the location
		- type String
- UpdateLocation(String):
	- Update the location and return true if the update was successful, false otherwise
	- GetStoreId
		- return the id of this store
			- type String

# Ratable (interface)
- GetRatings():
	- return a list of ratings 
		- type List of Rating
- DeleteRating(Rating):
	- Delete given rating and return true if successful, false otherwise
- AddRating(Rating):
	- add given rating to ratings and return true if successful, false otherwise




