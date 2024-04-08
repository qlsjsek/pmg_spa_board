package com.pmg.exception;

import com.pmg.user.entity.User;

public class UserNotFoundException extends RuntimeException{
   
	private User user; 

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public void setData(User user) {
        this.user = user;
    }

    public User getData() {
        return user;
    }
}
