package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class UserPageController {

    @GetMapping("/user")
    public String userPage(Authentication authentication, Model model) {
        model.addAttribute("user", authentication.getPrincipal());
        return "user";
    }
}
