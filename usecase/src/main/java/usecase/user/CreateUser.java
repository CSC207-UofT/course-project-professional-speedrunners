package usecase.user;

import domain.entity.User;
import usecase.port.IdGenerator;
import usecase.port.UserDb;

public final class CreateUser {

    private final UserDb userDb;

    private final IdGenerator idGen;

    public CreateUser(final UserDb userDb, final IdGenerator idGen){
        this.userDb = userDb;
        this.idGen = idGen;
    }

    public User create(final User user){
        User userToAdd = User.builder()
                .setId(user.getId())
                .setName(user.getName())
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .build();

        return userDb.create(userToAdd);
    }


}
