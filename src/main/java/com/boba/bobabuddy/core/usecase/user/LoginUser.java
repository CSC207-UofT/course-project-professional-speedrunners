package com.boba.bobabuddy.core.usecase.user;

import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.port.userport.ILoginUser;

public class LoginUser implements ILoginUser {

    public boolean logIn(User user, String password){
        return (user.getPassword().equals(password));
    }

}
