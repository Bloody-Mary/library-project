package ru.babushkina.libraryproject.service;

import ru.babushkina.libraryproject.dto.GenreDto;

public interface GenreService {
    GenreDto getGenreById(Long id);
}
