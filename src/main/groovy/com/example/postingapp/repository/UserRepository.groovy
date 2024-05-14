package com.example.postingapp.repository

import com.example.postingapp.model.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository extends MongoRepository<User, Long> {

}
