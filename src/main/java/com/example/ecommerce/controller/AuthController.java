package com.example.ecommerce.controller;

import com.example.ecommerce.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            Model model
    ) {
        String error = userService.register(name, email, password);
        if (error != null) {
            model.addAttribute("error", error);
            return "register";
        }
        return "redirect:/login?registered";
    }
}
