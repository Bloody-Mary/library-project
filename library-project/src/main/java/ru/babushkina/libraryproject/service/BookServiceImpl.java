package ru.babushkina.libraryproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.babushkina.libraryproject.dto.BookDto;
import ru.babushkina.libraryproject.model.Book;
import ru.babushkina.libraryproject.repository.BookRepository;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;

    @Override
    public BookDto getByNameV1(String name) {
       Book book = bookRepository.findBookByName(name).orElseThrow();
       return convertEntityToDto(book);
    }

    private BookDto convertEntityToDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .genre(book.getGenre().getName())
                .name(book.getName())
                .build();
    }

    @Override
    public BookDto getByNameV2(String name) {
        Book book = bookRepository.findBookByNameBySQL(name).orElseThrow();
        return convertEntityToDto(book);
    }
}
