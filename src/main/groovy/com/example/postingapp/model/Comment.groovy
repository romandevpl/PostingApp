package com.example.postingapp.model

import org.springframework.data.annotation.Id

class Comment {
    @Id
    Long id
    String content

    Comment(String content) {
        this.content = content
    }

    protected Comment(){}
}
