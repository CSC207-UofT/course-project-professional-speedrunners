package domain.entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private List<RatingPoint> ratingLst;

    public User(final String id, final String name, final String email, final String password){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.ratingLst = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public static UserBuilder build(){return new UserBuilder();}

    public static class UserBuilder{
        private String id;
        private String name;
        private String email;
        private String password;

        public UserBuilder setId(final String id){
            this.id = id;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public User build(){return new User(this.id, this.name, this.email, this.password);}
    }

}
