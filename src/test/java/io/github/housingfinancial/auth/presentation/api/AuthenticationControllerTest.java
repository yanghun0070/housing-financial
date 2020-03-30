package io.github.housingfinancial.auth.presentation.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.housingfinancial.auth.presentation.vo.AuthenticationRequest;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Order(1)
    @Test
    public void signUpWithValidUserThenSuccessful() throws Exception {
        mockMvc.perform(post("/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(
                                        new AuthenticationRequest("user",
                                                                  "password"))))
               .andDo(print())
               .andExpect(status().is2xxSuccessful());
    }

    @Order(2)
    @Test
    public void signInWithValidUserThenSuccessful() throws Exception {
        mockMvc.perform(post("/auth/signin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(
                                        new AuthenticationRequest("user",
                                                                  "password"))))
               .andDo(print())
               .andExpect(status().is2xxSuccessful());
    }

    @Order(2)
    @Test
    public void signInWithInvalidUserThenUnauthenticated() throws Exception {
        mockMvc.perform(post("/auth/signin")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(objectMapper.writeValueAsString(
                                                      new AuthenticationRequest("invalid",
                                                                                "invalidpassword"))))
               .andDo(print())
               .andExpect(status().isUnauthorized());
    }

    @Order(2)
    @Test
    public void signInWithWrongPasswordThenUnauthenticated() throws Exception {
        mockMvc.perform(post("/auth/signin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(
                                        new AuthenticationRequest("user",
                                                                  "wrongpassword"))))
               .andDo(print())
               .andExpect(status().isUnauthorized());
    }

}
