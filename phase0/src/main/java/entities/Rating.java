package entities;

public class Rating{
    int rating;
    User user;
    String id;

    public Rating(int rating, User user, String id){
        this.rating = rating;
        this.user = user;
        this.id = id;
    }

    public boolean updateRating(int newRating) {
        this.rating = newRating;
        return true;
    }

    public int getRating() {
        return rating;
    }

    public User getUser() {
        return user;
    }
}
