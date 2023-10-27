package ru.babushkina.simpleformproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.babushkina.simpleformproject.model.UsersModel;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UsersModel, Integer> {
    Optional<UsersModel> findByLoginAndPassword(String login, String password);
}
