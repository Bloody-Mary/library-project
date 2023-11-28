package ru.babushkina.libraryproject.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.babushkina.libraryproject.dto.BookDto;
import ru.babushkina.libraryproject.model.Author;
import ru.babushkina.libraryproject.model.Book;
import ru.babushkina.libraryproject.model.Genre;
import ru.babushkina.libraryproject.repository.BookRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void testGetByNameV1() {
        Long id = 1L;
        String bookName = "Harry Potter";
        Genre genre = new Genre();
        Set<Author> books = new HashSet<>();
        Book book = new Book(id, bookName, genre, books);
        Optional<Book> optionalBook = Optional.of(book);

        when(bookRepository.findBookByName(bookName)).thenReturn(optionalBook);

        BookDto bookDto = bookService.getByNameV1(bookName);

        assertNotNull(bookDto);
        assertEquals(bookDto.getName(), book.getName());

        verify(bookRepository, times(1)).findBookByName(bookName);
    }

    @Test
    public void testGetByNameV2() {
        Long id = 2L;
        String bookName = "Мастер и Маргарита";
        Genre genre = new Genre();
        Set<Author> books = new HashSet<>();
        Book book = new Book(id, bookName, genre, books);
        Optional<Book> optionalBook = Optional.of(book);

        when(bookRepository.findBookByNameBySQL(bookName)).thenReturn(optionalBook);

        BookDto bookDto = bookService.getByNameV2(bookName);

        assertNotNull(bookDto);
        assertEquals(bookDto.getName(), book.getName());

        verify(bookRepository, times(1)).findBookByNameBySQL(bookName);
    }
}
