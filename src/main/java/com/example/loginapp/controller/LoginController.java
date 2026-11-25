package com.example.loginapp.controller;

import com.example.loginapp.auth.AuthService;
import com.example.loginapp.controller.dto.LoginForm;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    /**
     * Session attribute key for the authenticated username.
     * Kept compatible with existing behavior while aligning with the new requirement.
     */
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

    /**
     * GET /login returns login view with an empty LoginForm.
     * If already authenticated, redirect to /welcome.
     */
    @GetMapping("/login")
    public String loginForm(HttpSession session, Model model) {
        if (session.getAttribute(AUTH_USER) != null) {
            return "redirect:/welcome";
        }
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    /**
     * POST /login binds @Valid LoginForm and checks BindingResult.
     * Authenticates via AuthService (backed by H2). On success sets session attribute and PRG redirect to /welcome.
     * On invalid credentials, return login view with model attribute loginError.
     */
    @PostMapping("/login")
    public String handleLogin(@Valid @ModelAttribute("loginForm") LoginForm loginForm,
                              BindingResult bindingResult,
                              HttpSession session,
                              Model model) {

        if (bindingResult.hasErrors()) {
            return "login";
        }

        boolean authenticated = authService.authenticate(loginForm.getUsername(), loginForm.getPassword());
        if (!authenticated) {
            model.addAttribute("loginError", true);
            return "login";
        }

        session.setAttribute(AUTH_USER, loginForm.getUsername());
        return "redirect:/welcome";
    }

    /**
     * GET /welcome checks session; if not authenticated redirect to /login; else render welcome with username.
     */
    @GetMapping("/welcome")
    public String welcome(HttpSession session, Model model) {
        Object authUser = session.getAttribute(AUTH_USER);
        if (authUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("username", authUser);
        return "welcome";
    }

    /**
     * POST /logout invalidates session and redirects to /login.
     */
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }
}
