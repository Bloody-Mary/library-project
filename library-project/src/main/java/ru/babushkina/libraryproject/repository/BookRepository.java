package ru.babushkina.libraryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.babushkina.libraryproject.model.Book;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findBookByName(String name);
}
