package ru.babushkina.libraryproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.babushkina.libraryproject.service.UserService;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;
}
