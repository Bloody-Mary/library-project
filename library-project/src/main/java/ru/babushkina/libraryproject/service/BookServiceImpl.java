package ru.babushkina.libraryproject.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Override
    public BookDto getByNameV1(String name) {
        log.info("Get book by name {}", name);
        Optional<Book> book = bookRepository.findBookByName(name);
        if (book.isPresent()) {
            BookDto bookDto = convertEntityToDto(book.get());
            log.info("Book: {}", bookDto.toString());
            return bookDto;
        } else {
            log.error("Book with name {} not found", name);
            throw new NoSuchElementException("No value present");
        }
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
        log.info("Get book by name {}", name);
        Optional<Book> book = bookRepository.findBookByNameBySQL(name);
        if (book.isPresent()) {
            BookDto bookDto = convertEntityToDto(book.get());
            log.info("Book: {}", bookDto.toString());
            return bookDto;
        } else {
            log.error("Book with name {} not found", name);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public BookDto getByNameV3(String name) {
        log.info("Get book by name {}", name);
        Specification<Book> specification = getBookByNameSpecification(name);
        Book book = bookRepository.findOne(specification).orElseThrow(() -> {
            log.error("Book with name {} not found", name);
            throw new NoSuchElementException("No value present");
        });
        BookDto bookDto = convertEntityToDto(book);
        log.info("Book: {}", bookDto.toString());
        return bookDto;
//        Specification<Book> specification = Specification.where(new Specification<Book>() {
//            @Override
//            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                return criteriaBuilder.equal(root.get("name"), name);
//            }
//        });
//        Book book = bookRepository.findOne(specification).orElseThrow();
//        return convertEntityToDto(book);
    }

    private Specification<Book> getBookByNameSpecification(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name);
    }

    @Override
    public BookDto createBook(BookCreateDto bookCreateDto) {
        log.info("Create book: {}", bookCreateDto.toString());
        Book book = convertDtoToEntity(bookCreateDto);
        book = bookRepository.save(book);
        BookDto bookDto = convertEntityToDto(book);
        log.info("Book created: {}", bookDto.toString());
        return bookDto;
    }

    private Book convertDtoToEntity(BookCreateDto bookCreateDto) {
        Author author = authorRepository.findById(bookCreateDto.getAuthorId()).orElseThrow();
        Genre genre = genreRepository.findById(bookCreateDto.getGenreId()).orElseThrow();

        return Book.builder()
                .name(bookCreateDto.getName())
                .genre(genre)
                .authors(Set.of(author))
                .build();
    }

    private BookDto convertSavedEntityToDto(Book book) {
        List<AuthorDto> authorDtoList = null;
        if (book.getAuthors() != null) {
            authorDtoList = book.getAuthors()
                    .stream()
                    .map(author -> AuthorDto.builder()
                            .id(author.getId())
                            .name(author.getName())
                            .surname(author.getSurname())
                            .build())
                    .toList();
        }

        BookDto bookDto = BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .genre(book.getGenre().getName())
                .authors(authorDtoList)
                .build();
        return bookDto;
    }

    @Override
    public BookDto updateBook(BookUpdateDto bookUpdateDto) {
        log.info("Update book: {}", bookUpdateDto);
        Book book = bookRepository.findById(bookUpdateDto.getId()).orElseThrow(() -> {
            log.error("Book with id {} not found", bookUpdateDto.getId());
            return new NoSuchElementException("No value present");
        });
        book.setName(bookUpdateDto.getName());
        book.setId(bookUpdateDto.getId());
        Book savedBook = bookRepository.save(book);
        BookDto bookDto = convertEntityToDto(savedBook);
        log.info("Book updated: {}", bookDto);
        return bookDto;
    }

    @Override
    public void deleteBook(Long id) {
        log.info("Delete book with id: {}", id);
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            bookRepository.deleteById(id);
            log.info("Book with id {} was deleted", id);
        } else {
            log.error("An error acquired while deleting book with id {}", id);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public List<BookDto> getAllBooks() {
        log.info("Get all books");
        List<Book> books = bookRepository.findAll();
        List<BookDto> bookDtos = books.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
        log.info("Found {} books", bookDtos.size());
        return bookDtos;
    }
}
