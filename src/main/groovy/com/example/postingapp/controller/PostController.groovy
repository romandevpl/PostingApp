package com.example.postingapp.controller

import com.example.postingapp.exception.PostNotFoundException
import com.example.postingapp.exception.UserNotFoundEception
import com.example.postingapp.model.Post
import com.example.postingapp.model.User
import com.example.postingapp.repository.PostRepository
import com.example.postingapp.repository.UserRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users/{userId}/posts")
class PostController {
    private PostRepository postRepository
    private UserRepository userRepository

    PostController(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository
        this.userRepository = userRepository
    }

    @GetMapping
    List<Post> getAllPosts(@PathVariable("userId") Long userId) {
        findUser(userId).getPosts()
    }

    @GetMapping("/{postId}")
    Post getPost(@PathVariable("postId") Long postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId))
    }

    @PostMapping
    Post createPost(@PathVariable("userId") Long id, @RequestBody Post newPost) {
        User user = findUser(id)
        List<Post> posts = user.getPosts()
        posts = (posts == null) ? List.of(newPost) : posts + posts.addLast(newPost)
        user.setPosts(posts)
        userRepository.save(user)
        postRepository.save(newPost)
    }

    @PutMapping("/{id}")
    Post editPost(@RequestBody Post newPost, @PathVariable("id") Long id) {
        postRepository.findById(id)
                .map(post -> {
                    post.setContent(newPost.content)
                    postRepository.save(post)
                })
                .orElseGet({
                    newPost.setId(id)
                    () -> postRepository.save(newPost)
                })
    }

    @DeleteMapping("/{id}")
    Post deletePost(@PathVariable("userId") Long userId, @PathVariable("id") Long postId) {
        User user = findUser(userId)
        user.setPosts(user.getPosts()?.findAll{ it.id != postId })
        userRepository.save(user)
        postRepository.deleteById(postId)
    }

    private User findUser(long id) {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundEception(id))
    }
}
