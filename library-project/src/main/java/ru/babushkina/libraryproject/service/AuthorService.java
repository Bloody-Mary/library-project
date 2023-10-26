package ru.babushkina.libraryproject.service;

import ru.babushkina.libraryproject.dto.AuthorDto;
import ru.babushkina.libraryproject.model.Author;

public interface AuthorService {
    AuthorDto getAuthorById(Long id);
    AuthorDto getByNameV1(String name);

    AuthorDto getByNameV1(Author author);
}
