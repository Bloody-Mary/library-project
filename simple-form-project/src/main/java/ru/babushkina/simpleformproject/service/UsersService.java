package ru.babushkina.simpleformproject.service;

import org.springframework.stereotype.Service;
import ru.babushkina.simpleformproject.model.UsersModel;

@Service
public class UsersService {
    public UsersModel registerUser(String login, String password, String email) {
        if (login != null && password != null) {
            UsersModel usersModel = new UsersModel();
            usersModel.setLogin(login);
            usersModel.setPassword(password);
            usersModel.setEmail(email);

        }
    }
}
