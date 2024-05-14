package com.example.postingapp.exception

class PostNotFoundException extends RuntimeException{
    PostNotFoundException(Long id){
        super("Could not found post " + id)
    }
}
