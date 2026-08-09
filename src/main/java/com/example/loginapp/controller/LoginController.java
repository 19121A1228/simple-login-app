package com.example.loginapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    // Show the empty login form
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // No validation - any username/password submitted is accepted and
    // taken straight to the welcome page.
    @PostMapping("/login")
    public String handleLogin(@RequestParam String username,
                               @RequestParam String password,
                               Model model) {
        model.addAttribute("username", username);
        return "welcome";
    }

    // Root just redirects to the login page
    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }
}
