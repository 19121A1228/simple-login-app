package com.example.loginapp;

package com.example.loginapp;

import com.example.loginapp.controller.LoginController;
import com.example.loginapp.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Test
    void postLoginBlankUsernameShowsLoginWithError() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", " ")
                        .param("password", "whatever"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    void postLoginInvalidCredentialsShowsLoginWithError() throws Exception {
        when(authService.authenticate(anyString(), anyString())).thenReturn(false);

        mockMvc.perform(post("/login")
                        .param("username", "whoever")
                        .param("password", "wrong"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    void postLoginValidCredentialsRedirectsToWelcomeAndStoresUserInSession() throws Exception {
        when(authService.authenticate("whoever", "whatever")).thenReturn(true);

        MockHttpSession session = (MockHttpSession) mockMvc.perform(post("/login")
                        .param("username", "whoever")
                        .param("password", "whatever"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/welcome"))
                .andReturn()
                .getRequest()
                .getSession(false);

        // Ensure the controller stored the authenticated user in the session
        org.junit.jupiter.api.Assertions.assertNotNull(session);
        org.junit.jupiter.api.Assertions.assertNotNull(session.getAttribute("AUTH_USER"));
    }

    @Test
    void getWelcomeWithoutSessionRedirectsToLogin() throws Exception {
        mockMvc.perform(get("/welcome"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void getWelcomeWithSessionShowsWelcomeAndIncludesUsername() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("AUTH_USER", "whoever");

        mockMvc.perform(get("/welcome").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("welcome"))
                .andExpect(model().attributeExists("username"));
    }

    @Test
    void postLogoutInvalidatesSessionAndRedirectsToLogin() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("AUTH_USER", "whoever");

        mockMvc.perform(post("/logout").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        org.junit.jupiter.api.Assertions.assertTrue(session.isInvalid());
    }
}
