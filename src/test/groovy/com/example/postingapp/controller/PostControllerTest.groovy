package com.example.postingapp.controller

import com.example.postingapp.model.Post
import com.example.postingapp.model.User
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Shared
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest extends Specification {
    @Autowired
    private MockMvc mvc
    @Shared
    def url = "http://localhost:8080/users/4/posts"

    def "Create post"() {
        expect: "Status is 200"
        mvc.perform(MockMvcRequestBuilders
                .post(url)
                .content(asJsonString(new Post(5, "Post nr 5")))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
    }

    def "Show all posts of user"(){
        expect: "Status is 200"
        mvc.perform(MockMvcRequestBuilders
                .get(url))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("\$", Matchers.hasSize(2)))
    }

    def "Update post of a user"() {
        given:
        long postId = 5
        def newContent = "I am updating my post"
        expect: "Status is 200 and user post is updated"
        mvc.perform(MockMvcRequestBuilders
                .put(url + "/" + postId)
                .content(asJsonString(new Post(newContent)))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.id").value(postId))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.content").value(newContent))
    }

    def "Delete post of a user"() {
        given:
        long postId = 5
        expect: "Status is 200"
        mvc.perform(MockMvcRequestBuilders
                .delete(url + "/" + postId))
            .andExpect(status().isOk())
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
