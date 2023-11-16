package ru.babushkina.libraryproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.babushkina.libraryproject.model.User;
import ru.babushkina.libraryproject.service.UserService;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }
}
