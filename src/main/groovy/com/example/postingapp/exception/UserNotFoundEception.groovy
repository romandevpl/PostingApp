package com.example.postingapp.exception

class UserNotFoundEception extends RuntimeException{
    UserNotFoundEception(Long id) {
        super("Could not find user "  + id)
    }
}
