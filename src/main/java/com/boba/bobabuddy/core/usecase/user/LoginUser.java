package com.boba.bobabuddy.core.usecase.user;

import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.core.usecase.port.userport.ILoginUser;

public class LoginUser implements ILoginUser {
    private User user;

    public LoginUser(User user){
        this.user = user;
    }

    public boolean logIn(String password){
        return (user.getPassword() == password);
    }

}
