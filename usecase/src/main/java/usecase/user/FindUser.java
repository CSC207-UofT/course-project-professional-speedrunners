package usecase.user;

import domain.entity.User;
import usecase.port.UserDb;

import java.util.List;
import java.util.Optional;

public final class FindUser {

    private final UserDb userDb;

    public FindUser(UserDb userDb){
        this.userDb = userDb;
    }

    public Optional<User> findById(String id){
        return userDb.findById(id);
    }

    public Optional<User> findByEmail(String email){
        return userDb.findByEmail(email);
    }

    public Optional<List<User>> findByName(String name){
        return userDb.findByName(name);
    }

    public List<User> findAll(){
        return userDb.findAll();
    }
}
