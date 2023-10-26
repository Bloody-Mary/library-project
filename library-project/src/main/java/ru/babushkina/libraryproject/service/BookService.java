package ru.babushkina.libraryproject.service;

import ru.babushkina.libraryproject.dto.BookDto;

public interface BookService {
    BookDto getByNameV1(String name);
}
