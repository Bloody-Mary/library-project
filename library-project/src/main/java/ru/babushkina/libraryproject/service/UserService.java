package ru.babushkina.libraryproject.service;

import ru.babushkina.libraryproject.model.User;

public interface UserService {
    User findByUsername(String username);
}
