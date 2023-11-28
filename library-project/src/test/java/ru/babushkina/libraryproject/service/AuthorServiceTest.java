package ru.babushkina.libraryproject.service;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import ru.babushkina.libraryproject.dto.AuthorCreateDto;
import ru.babushkina.libraryproject.dto.AuthorDto;
import ru.babushkina.libraryproject.dto.BookDto;
import ru.babushkina.libraryproject.model.Author;
import ru.babushkina.libraryproject.model.Book;
import ru.babushkina.libraryproject.repository.AuthorRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
        assertEquals(authorDto.getId(), author.getId());
        assertEquals(authorDto.getName(), author.getName());
        assertEquals(authorDto.getSurname(), author.getSurname());
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
        assertEquals(authorDto.getId(), author.getId());
        assertEquals(authorDto.getName(), author.getName());
        assertEquals(authorDto.getSurname(), author.getSurname());
    }

    @Test
    public void testGetByNameV1NotFound() {
        String name = "Andrew";
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
        assertEquals(authorDto.getId(), author.getId());
        assertEquals(authorDto.getName(), author.getName());
        assertEquals(authorDto.getSurname(), author.getSurname());
    }

    @Test
    public void testGetByNameV2NotFound() {
        String name = "Alex";
        when(authorRepository.findAuthorByName(name)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> authorService.getByNameV2(name));
        verify(authorRepository).findAuthorByName(name);
    }

    @Test
    public void testGetByNameV3() {
        // Создание тестовых данных
        Long id = 4L;
        String name = "Samuel";
        String surname = "Jackson";
        Set<Book> books = new HashSet<>();
        List<BookDto> booksList = new ArrayList<>();
        Author author = new Author(id, name, surname, books);
        AuthorDto authorDto = new AuthorDto(id, name, surname, booksList);
        // Мокирование authorRepository
        Specification<Author> specification = getSpecificationByName(name);
        Mockito.when(authorRepository.findOne(specification)).thenReturn(Optional.of(author));

        // Вызов тестируемого метода
        AuthorDto result = authorService.getByNameV3(name);

        // Проверка результатов
        Assertions.assertNotNull(result);
        Assertions.assertEquals(authorDto.getId(), result.getId());
        Assertions.assertEquals(authorDto.getName(), result.getName());
        Assertions.assertEquals(authorDto.getSurname(), result.getSurname());

        // Проверка вызовов методов
        Mockito.verify(authorRepository).findOne(specification);
    }

    @Test
    public void testGetByNameV3NotFound() {
        String name = "Samuel";
        Specification<Author> specification = getSpecificationByName(name);
        Mockito.when(authorRepository.findOne(specification)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> authorService.getByNameV3(name));

        Mockito.verify(authorRepository).findOne(specification);
    }

    private Specification<Author> getSpecificationByName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name);
    }

    @Test
    public void testCreateAuthor() {
        AuthorCreateDto authorCreateDto = new AuthorCreateDto();

        Author author = new Author();

        AuthorDto expectedAuthorDto = new AuthorDto();
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        AuthorDto actualAuthorDto = authorService.createAuthor(authorCreateDto);

        assertEquals(expectedAuthorDto, actualAuthorDto);
        verify(authorRepository, times(1)).save(any(Author.class));
    }

}
