package com.example.loginapp.controller;

import com.example.loginapp.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private static final String SESSION_USERNAME_KEY = "username";

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    // Show the empty login form (or redirect if already authenticated)
    @GetMapping("/login")
    public String loginForm(HttpSession session) {
        if (session.getAttribute(SESSION_USERNAME_KEY) != null) {
            return "redirect:/welcome";
        }
        return "login";
    }

    // Validate credentials. On success, store username in session and redirect.
    // On failure, return login with a generic error message and keep username populated.
    @PostMapping("/login")
    public String handleLogin(@RequestParam String username,
                              @RequestParam String password,
                              Model model,
                              HttpSession session) {
        if (authService.isValid(username, password)) {
            session.setAttribute(SESSION_USERNAME_KEY, username);
            return "redirect:/welcome";
        }

        model.addAttribute("username", username);
        model.addAttribute("error", "Invalid username or password.");
        // Do not echo password back to the view.
        return "login";
    }

    // Welcome page requires authentication (session attribute present)
    @GetMapping("/welcome")
    public String welcome(HttpSession session, Model model) {
        Object username = session.getAttribute(SESSION_USERNAME_KEY);
        if (username == null) {
            return "redirect:/login";
        }
        model.addAttribute("username", username);
        return "welcome";
    }

    // Logout invalidates session and redirects to login
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // Root just redirects to the login page
    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }
}
