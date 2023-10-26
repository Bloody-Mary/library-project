package ru.babushkina.libraryproject.service;

import ru.babushkina.libraryproject.dto.BookDto;

public interface BookService {
    BookDto getByNameV1(String name);
    BookDto getByNameV2(String name);
    BookDto getByNameV3(String name);
}
