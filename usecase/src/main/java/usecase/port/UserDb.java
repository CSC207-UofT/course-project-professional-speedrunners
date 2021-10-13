package usecase.port;

import domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDb {
    User create(User userToAdd);

    Optional<User> findById(String id);

    Optional<User> findByEmail(String email);

    Optional<List<User>> findByName(String name);

    List<User> findAll();
}
