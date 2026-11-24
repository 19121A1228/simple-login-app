package com.example.loginapp.controller;

import com.example.loginapp.UserStore;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    public static final String AUTHENTICATED_USER = "AUTHENTICATED_USER";

    private final UserStore userStore;

    public LoginController(UserStore userStore) {
        this.userStore = userStore;
    }

    // Root just redirects to the login page
    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    // Show the empty login form
    @GetMapping("/login")
    public String loginForm(HttpSession session) {
        if (session.getAttribute(AUTHENTICATED_USER) != null) {
            return "redirect:/welcome";
        }
        return "login";
    }

    // Validate username/password. On success store username in session and redirect to welcome.
    @PostMapping("/login")
    public String handleLogin(@RequestParam String username,
                              @RequestParam String password,
                              HttpSession session,
                              Model model) {

        String trimmedUsername = StringUtils.trimWhitespace(username);

        if (!userStore.isValid(username, password)) {
            model.addAttribute("error", "Invalid username or password");
            model.addAttribute("username", trimmedUsername);
            return "login";
        }

        session.setAttribute(AUTHENTICATED_USER, trimmedUsername);
        return "redirect:/welcome";
    }

    @GetMapping("/welcome")
    public String welcome(HttpSession session, Model model) {
        Object authUser = session.getAttribute(AUTHENTICATED_USER);
        if (authUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("username", authUser);
        return "welcome";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }
}
