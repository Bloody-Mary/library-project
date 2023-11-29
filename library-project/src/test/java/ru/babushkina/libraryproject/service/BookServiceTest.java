package ru.babushkina.libraryproject.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import ru.babushkina.libraryproject.dto.AuthorDto;
import ru.babushkina.libraryproject.dto.BookCreateDto;
import ru.babushkina.libraryproject.dto.BookDto;
import ru.babushkina.libraryproject.dto.BookUpdateDto;
import ru.babushkina.libraryproject.model.Author;
import ru.babushkina.libraryproject.model.Book;
import ru.babushkina.libraryproject.model.Genre;
import ru.babushkina.libraryproject.repository.AuthorRepository;
import ru.babushkina.libraryproject.repository.BookRepository;
import ru.babushkina.libraryproject.repository.GenreRepository;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private GenreRepository genreRepository;

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

    @Test
    public void testGetByNameV3() {
        Long id = 3L;
        String bookName = "Собачье сердце";
        Genre genre = new Genre();
        Set<Author> books = new HashSet<>();
        Book book = new Book(id, bookName, genre, books);
        Specification<Book> specification = getBookByNameSpecification(bookName);
        Optional<Book> optionalBook = Optional.of(book);

        when(bookRepository.findOne(specification)).thenReturn(optionalBook);

        BookDto bookDto = bookService.getByNameV3(bookName);

        assertNotNull(bookDto);
        assertEquals(bookDto.getName(), book.getName());

        verify(bookRepository, times(1)).findOne(specification);
    }

    @Test
    public void testGetByNameV3NotFound() {
        String bookName = "Non-existent Book";
        Specification<Book> specification = getBookByNameSpecification(bookName);
        Optional<Book> optionalBook = Optional.empty();

        when(bookRepository.findOne(specification)).thenReturn(optionalBook);

        assertThrows(NoSuchElementException.class, () -> {
            bookService.getByNameV3(bookName);
        });

        verify(bookRepository, times(1)).findOne(specification);
    }

    private Specification<Book> getBookByNameSpecification(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name);
    }

    @Test
    public void testCreateBook() {
        // Arrange
        BookCreateDto bookCreateDto = new BookCreateDto();
        Book book = new Book();
        BookDto expectedBookDto = new BookDto();

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // Act
        BookDto actualBookDto = bookService.createBook(bookCreateDto);

        // Assert
        assertEquals(expectedBookDto, actualBookDto);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void testUpdateBook() {
        Long bookId = 1L;
        String bookName = "Updated Book";
        Genre genre = new Genre();
        Set<Author> books = new HashSet<>();
        BookUpdateDto bookUpdateDto = new BookUpdateDto(bookId, bookName);
        Book book = new Book(bookId, "Existing Book", genre, books);
        Book updatedBook = new Book(bookId, bookName, genre, books);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(updatedBook);

        BookDto updatedBookDto = bookService.updateBook(bookUpdateDto);

        assertNotNull(updatedBookDto);
        assertEquals(updatedBookDto.getId(), updatedBook.getId());
        assertEquals(updatedBookDto.getName(), updatedBook.getName());

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void testDeleteBook() {
        Long bookId = 1L;
        Genre genre = new Genre();
        Set<Author> books = new HashSet<>();
        Optional<Book> book = Optional.of(new Book(bookId, "Example Book", genre, books));

        when(bookRepository.findById(bookId)).thenReturn(book);
        assertDoesNotThrow(() -> {
            bookService.deleteBook(bookId);
        });

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).deleteById(bookId);
    }

    @Test
    public void testDeleteBookNotFound() {
        Long bookId = 1L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {bookService.deleteBook(bookId);
        });

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, never()).deleteById(anyLong());
    }
}
