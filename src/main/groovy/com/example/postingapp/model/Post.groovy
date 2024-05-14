package com.example.postingapp.model

import org.springframework.data.annotation.Id

class Post {
    @Id
    Long id
    String content
    List<Comment> comments

    Post(Long id, String content) {
        this.id = id
        this.content = content
    }

    Post(String content) {
        this.content = content
    }
    protected Post(){}
}
