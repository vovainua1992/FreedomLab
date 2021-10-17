package com.freedom.services.utils;

import com.freedom.services.dommain.User;

public class UserUtils {

    public static boolean equalsUser(User user, User user1) {
        if (user != null && user1 != null)
            if (user.getEmail().equals(user1.getEmail())
                    && user.getPassword().equals(user1.getPassword())
                    && user.getUsername().equals(user1.getUsername()))
                return true;
        return false;
    }

    public static User newUser(String name,String password,String email){
        User user = new User();
        user.setUsername(name);
        user.setPassword(password);
        user.setEmail(email);
        return user;
    }
}
