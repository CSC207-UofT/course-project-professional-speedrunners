package com.boba.bobabuddy.core.usecase.user;

import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.user.port.ILoginUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class LoginUser implements ILoginUser {

    public boolean logIn(User user, String password) {
        return (user.getPassword().equals(password));
    }

}
