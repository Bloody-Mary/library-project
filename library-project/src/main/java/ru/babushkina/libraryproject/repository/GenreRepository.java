package ru.babushkina.libraryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.babushkina.libraryproject.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
