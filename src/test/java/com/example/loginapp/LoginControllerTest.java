package com.example.loginapp;

import com.example.loginapp.controller.LoginController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getLoginShowsForm() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void anyCredentialsReachWelcomePage() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "whoever")
                        .param("password", "whatever"))
                .andExpect(status().isOk())
                .andExpect(view().name("welcome"));
    }

    @Test
    void emptyLookingCredentialsStillReachWelcomePage() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "x")
                        .param("password", "y"))
                .andExpect(status().isOk())
                .andExpect(view().name("welcome"));
    }
}
