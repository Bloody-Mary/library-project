package ru.babushkina.libraryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.babushkina.libraryproject.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
