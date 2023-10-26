package ru.babushkina.libraryproject.service;

import ru.babushkina.libraryproject.dto.AuthorDto;

public interface AuthorService {
    AuthorDto getAuthorById(Long id);
    AuthorDto getByNameV1(String name);
}
