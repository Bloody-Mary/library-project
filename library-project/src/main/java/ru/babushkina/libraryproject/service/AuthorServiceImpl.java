package ru.babushkina.libraryproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.babushkina.libraryproject.dto.AuthorDto;
import ru.babushkina.libraryproject.dto.BookDto;
import ru.babushkina.libraryproject.model.Author;
import ru.babushkina.libraryproject.repository.AuthorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService{
    private final AuthorRepository authorRepository;

    @Override
    public AuthorDto getAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow();
        return convertToDto(author);
    }

    @Override
    public AuthorDto getByNameV1(String name) {
        Author author = authorRepository.findAuthorByName(name).orElseThrow();
        return convertEntityToDto(author);
    }

    private AuthorDto convertToDto(Author author) {
        List<BookDto> bookDtoList = author.getBooks()
                .stream()
                .map(book -> BookDto.builder()
                        .genre(book.getGenre().getName())
                        .name(book.getName())
                        .id(book.getId())
                        .build()
                ).toList();
        return AuthorDto.builder()
                .books(bookDtoList)
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .build();
    }
}
