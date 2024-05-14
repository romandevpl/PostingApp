package com.example.postingapp.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef

class User {
    @Id
    Long id
    String name
    String surname
    String email
    @DBRef
    List<Post> posts

    User(Long id, String name, String surname) {
        this.id = id
        this.name = name
        this.surname = surname
    }

    User(String email, String surname, String name) {
        this.email = email
        this.surname = surname
        this.name = name
    }

    User(String name, String surname) {
        this.name = name
        this.surname = surname
    }

    protected User() {}
}
