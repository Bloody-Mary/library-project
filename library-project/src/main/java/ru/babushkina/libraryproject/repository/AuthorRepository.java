package ru.babushkina.libraryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.babushkina.libraryproject.model.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findAuthorByName(String name);
}
