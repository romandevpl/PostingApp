package com.example.postingapp.repository

import com.example.postingapp.model.Post
import org.springframework.data.mongodb.repository.MongoRepository

interface PostRepository extends MongoRepository<Post, Long>{

}