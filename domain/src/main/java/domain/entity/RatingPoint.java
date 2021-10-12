package domain.entity;

public class RatingPoint {
    private int rating;
    private User user;
    private Ratable ratable;
    private String id;

    public RatingPoint(int rating, User user, String id, Ratable ratable){
        this.rating = rating;
        this.user = user;
        this.id = id;
        this.ratable = ratable;
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

    public Ratable getRatable() {
        return ratable;
    }

    public String getId() {
        return id;
    }

    public static RatingPointBuilder builder(){return new RatingPointBuilder();}

    public static class RatingPointBuilder{
        private int rating;
        private User user;
        private Ratable ratable;
        private String id;

        public RatingPointBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public RatingPointBuilder setRatable(Ratable ratable) {
            this.ratable = ratable;
            return this;
        }

        public RatingPointBuilder setRating(int rating) {
            this.rating = rating;
            return this;
        }

        public RatingPointBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public RatingPoint build(){return new RatingPoint(this.rating,this.user,this.id,this.ratable);}

    }
}
