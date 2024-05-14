package com.example.postingapp.controller

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
class UserControllerTest extends Specification {
    @Autowired
    private MockMvc mvc

    @Shared
    def url = "http://localhost:8080/users"

    def "Create user"() {
        expect: "Status is 200"
        mvc.perform(MockMvcRequestBuilders
                .post(url)
                .content(asJsonString(new User(4, "Antonio", "Moreno")))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
    }

    def "Show all users"() {
        expect: "Status is 200 and response is a list"
        mvc.perform(MockMvcRequestBuilders
                .get(url))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("\$", Matchers.hasSize(3)))
    }

    def "Show specific user"() {
        given:
        long userId = 4
        expect: "Status is 200 and response is a single object"
        mvc.perform(MockMvcRequestBuilders
                .get(url + "/" + userId))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.id").value(4))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value("Antonio"))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.surname").value("Moreno"))
    }

    def "Update user"() {
        given:
        long userId = 4
        def newEmail = "antonio.moreno@yahoo.com"
        expect: "Status is 200 and user email is updated"
        mvc.perform(MockMvcRequestBuilders
                .put(url + "/" + userId)
                .content(asJsonString(new User(newEmail, "Antonio", "Moreno")))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.id").value(4))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.email").value(newEmail))
    }

    def "Delete user"() {
        given:
        long userId = 4
        expect: "Status is 200"
        mvc.perform(MockMvcRequestBuilders
                .delete(url + "/" + userId))
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
