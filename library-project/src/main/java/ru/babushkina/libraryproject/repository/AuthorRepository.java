package ru.babushkina.libraryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.babushkina.libraryproject.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
