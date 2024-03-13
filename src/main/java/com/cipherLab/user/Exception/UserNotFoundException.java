package com.cipherLab.user.Exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id) {
        super("Could not find user " + id);
    }
    public UserNotFoundException(String cell) {
        super("Could not find user associated with cellNumber : " + cell);
    }
}
