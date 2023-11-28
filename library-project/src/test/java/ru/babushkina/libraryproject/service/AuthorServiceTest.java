package ru.babushkina.libraryproject.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import ru.babushkina.libraryproject.dto.AuthorDto;
import ru.babushkina.libraryproject.model.Author;
import ru.babushkina.libraryproject.model.Book;
import ru.babushkina.libraryproject.repository.AuthorRepository;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthorServiceTest {
    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    public void testGetAuthorById() {
        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        Set<Book> books = new HashSet<>();
        Author author = new Author(id, name, surname, books);


        when(authorRepository.findById(id)).thenReturn(Optional.of(author));

        AuthorDto authorDto = authorService.getAuthorById(id);

        verify(authorRepository).findById(id);
        Assertions.assertEquals(authorDto.getId(), author.getId());
        Assertions.assertEquals(authorDto.getName(), author.getName());
        Assertions.assertEquals(authorDto.getSurname(), author.getSurname());
    }

    @Test
    public void testGetAuthorByIdNotFound() {
        Long id = 1L;
        when(authorRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalStateException.class, () -> authorService.getAuthorById(id));

        verify(authorRepository).findById(id);
    }
    @Test
    public void testGetByNameV1() {
        Long id = 2L;
        String name = "Andrew";
        String surname = "Smith";
        Set<Book> books = new HashSet<>();
        Author author = new Author(id, name, surname, books);
        when(authorRepository.findAuthorByName(name)).thenReturn(Optional.of(author));

        AuthorDto authorDto = authorService.getByNameV1(name);

        verify(authorRepository).findAuthorByName(name);
        Assertions.assertEquals(authorDto.getId(), author.getId());
        Assertions.assertEquals(authorDto.getName(), author.getName());
        Assertions.assertEquals(authorDto.getSurname(), author.getSurname());
    }

    @Test
    public void testGetByNameV1NotFound() {
        String name = "Джон";
        when(authorRepository.findAuthorByName(name)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> authorService.getByNameV1(name));

        verify(authorRepository).findAuthorByName(name);
    }

    @Test
    public void testGetByNameV2() {
        Long id = 3L;
        String name = "Alex";
        String surname = "Kane";
        Set<Book> books = new HashSet<>();
        Author author = new Author(id, name, surname, books);
        when(authorRepository.findAuthorByName(name)).thenReturn(Optional.of(author));

        AuthorDto authorDto = authorService.getByNameV2(name);

        verify(authorRepository).findAuthorByName(name);
        Assertions.assertEquals(authorDto.getId(), author.getId());
        Assertions.assertEquals(authorDto.getName(), author.getName());
        Assertions.assertEquals(authorDto.getSurname(), author.getSurname());
    }

    @Test
    public void testGetByNameV2NotFound() {
        String name = "Alex";
        when(authorRepository.findAuthorByName(name)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> authorService.getByNameV2(name));
        verify(authorRepository).findAuthorByName(name);
    }
}
