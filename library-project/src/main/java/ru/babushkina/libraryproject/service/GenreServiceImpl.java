package ru.babushkina.libraryproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.babushkina.libraryproject.dto.AuthorDto;
import ru.babushkina.libraryproject.dto.BookDto;
import ru.babushkina.libraryproject.dto.GenreDto;
import ru.babushkina.libraryproject.model.Author;
import ru.babushkina.libraryproject.model.Book;
import ru.babushkina.libraryproject.model.Genre;
import ru.babushkina.libraryproject.repository.AuthorRepository;
import ru.babushkina.libraryproject.repository.BookRepository;
import ru.babushkina.libraryproject.repository.GenreRepository;
import java.util.stream.Collectors;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public GenreDto getGenreById(Long id) {
        Genre genre = genreRepository.findById(id).orElseThrow();
        return convertToDto(genre);
    }

    private GenreDto convertToDto(Genre genre) {
        List<BookDto> bookDtoList = genre.getBooks()
                .stream()
                .map(book -> convertToBookDto(book))
                .collect(Collectors.toList());
        return GenreDto.builder()
                .id(genre.getId())
                .name(genre.getName())
                .genreBooks(bookDtoList)
                .build();
    }

    private BookDto convertToBookDto(Book book) {
        List<AuthorDto> authorDtoList = book.getAuthors()
                .stream()
                .map(author -> convertToAuthorDto(author))
                .collect(Collectors.toList());
        return BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .genre(book.getGenre().getName())
                .authors(authorDtoList)
                .build();
    }
}
