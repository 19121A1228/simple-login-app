package com.example.loginapp.controller;

import com.example.loginapp.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    public static final String AUTH_USER = "AUTH_USER";

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    // Root just redirects to the login page
    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    // Show the empty login form
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // Validate username/password. On success store username in session and redirect to welcome.
    @PostMapping("/login")
    public String handleLogin(@RequestParam String username,
                              @RequestParam String password,
                              HttpSession session,
                              Model model) {

        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            model.addAttribute("error", "Username and password are required.");
            model.addAttribute("username", username);
            return "login";
        }

        if (!authService.isValid(username, password)) {
            model.addAttribute("error", "Invalid username or password.");
            model.addAttribute("username", username);
            return "login";
        }

        session.setAttribute(AUTH_USER, username);
        return "redirect:/welcome";
    }

    @GetMapping("/welcome")
    public String welcome(HttpSession session, Model model) {
        Object authUser = session.getAttribute(AUTH_USER);
        if (authUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("username", authUser);
        return "welcome";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
