package com.example.postingapp.controller

import com.example.postingapp.exception.UserNotFoundEception
import com.example.postingapp.model.User
import com.example.postingapp.repository.UserRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController {
    private UserRepository repository

    UserController(UserRepository repository) {
        this.repository = repository
    }

    @GetMapping
    List<User> getAllUsers() {
        repository.findAll()
    }

    @GetMapping("/{id}")
    User getUser(@PathVariable("id") Long id) {
        repository.findById(id)
                .orElseThrow(() -> new UserNotFoundEception(id))
    }

    @PostMapping
    User createUser(@RequestBody User newUser) {
        repository.save(newUser)
    }

    @PutMapping("/{id}")
    User editUser(@RequestBody User newUser, @PathVariable("id") Long id) {
        repository.findById(id)
                .map(user -> {
                    user.setName(newUser.name)
                    user.setSurname(newUser.surname)
                    user.setEmail(newUser?.email)
                    repository.save(user)
                })
                .orElseGet({
                    newUser.setId(id)
                    () -> repository.save(newUser)
                })
    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable("id") Long id) {
        repository.deleteById(id)
    }
}
