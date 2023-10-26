package ru.babushkina.libraryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.babushkina.libraryproject.model.Book;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findBookByName(String name);
    @Query(nativeQuery = true, value = "SELECT * FROM BOOK WHERE name = ?")
    Optional<Book> findBookByNameBySQL(String name);

}
