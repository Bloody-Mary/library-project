package ru.babushkina.libraryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.babushkina.libraryproject.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
