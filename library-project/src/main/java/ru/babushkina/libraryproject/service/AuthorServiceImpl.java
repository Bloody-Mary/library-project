package ru.babushkina.libraryproject.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.babushkina.libraryproject.dto.AuthorCreateDto;
import ru.babushkina.libraryproject.dto.AuthorDto;
import ru.babushkina.libraryproject.dto.AuthorUpdateDto;
import ru.babushkina.libraryproject.dto.BookDto;
import ru.babushkina.libraryproject.model.Author;
import ru.babushkina.libraryproject.repository.AuthorRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService{
    private final AuthorRepository authorRepository;

    @Override
    public AuthorDto getAuthorById(Long id) {
        log.info("Get author by id: {}", id);
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            AuthorDto authorDto = convertEntityToDto(author.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        } else {
            log.error("Author with id {} not found", id);
            throw new NoSuchElementException("No value present");
        }
//        Author author = authorRepository.findById(id).orElseThrow();
//        return convertToDto(author);
    }

    @Override
    public AuthorDto getByNameV1(String name) {
        log.info("Get author by name: {}", name);
        Optional<Author> author = authorRepository.findAuthorByName(name);
        if (author.isPresent()) {
            AuthorDto authorDto = convertEntityToDto(author.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        } else {
            log.error("Author with name {} not found", name);
            throw new NoSuchElementException("No value present");
        }
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

//    private AuthorDto convertEntityToDto(Author author) {
//        List<BookDto> bookDtoList = author.getBooks()
//                .stream()
//                .map(book -> BookDto.builder()
//                        .genre(book.getGenre().getName())
//                        .name(book.getName())
//                        .id(book.getId())
//                        .build()
//                ).toList();
//        return AuthorDto.builder()
//                .id(author.getId())
//                .name(author.getName())
//                .surname(author.getSurname())
//                .books(bookDtoList)
//                .build();
//    }

    @Override
    public AuthorDto getByNameV2(String name) {
        log.info("Get author by name: {}", name);
        Optional<Author> author = authorRepository.findAuthorByName(name);
        if (author.isPresent()) {
            AuthorDto authorDto = convertEntityToDto(author.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        } else {
            log.error("Author with name {} not found", name);
            throw new NoSuchElementException("No value present");
        }
//        Author author = authorRepository.findAuthorByNameBySQL(name).orElseThrow();
//        return convertEntityToDto(author);
    }

    @Override
    public AuthorDto getByNameV3(String name) {
        log.info("Get author by name: {}", name);
        Specification<Author> specification = getAuthorByNameSpecification(name);
        Author author = authorRepository.findOne(specification).orElseThrow(() -> {
            log.error("Author with name {} not found", name);
            return new NoSuchElementException("No value present");
        });
        AuthorDto authorDto = convertEntityToDto(author);
        log.info("Author: {}", authorDto.toString());
        return authorDto;
//        Specification<Author> specification = Specification.where(new Specification<Author>() {
//            @Override
//            public Predicate toPredicate(Root<Author> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                return criteriaBuilder.equal(root.get("name"), name);
//            }
//        });
//        Author author = authorRepository.findOne(specification).orElseThrow();
//        return convertEntityToDto(author);
    }

    private Specification<Author> getAuthorByNameSpecification(String name) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name));
    }

    @Override
    public AuthorDto createAuthor(AuthorCreateDto authorCreateDto) {
        log.info("Create author: {}", authorCreateDto.toString());
        Author author = convertDtoToEntity(authorCreateDto);
        author = authorRepository.save(author);
        AuthorDto authorDto = convertEntityToDto(author);
        log.info("Author created: {}", authorDto.toString());
        return authorDto;
//        Author author = authorRepository.save(convertDtoToEntity(authorCreateDto));
//        AuthorDto authorDto = convertEntityToDto(author);
//        return authorDto;
    }

    private Author convertDtoToEntity(AuthorCreateDto authorCreateDto) {
        return Author.builder()
                .name(authorCreateDto.getName())
                .surname(authorCreateDto.getSurname())
                .build();
    }

    private AuthorDto convertEntityToDto(Author author) {
        List<BookDto> bookDtoList = null;
        if (author.getBooks() != null) {
            bookDtoList = author.getBooks()
                    .stream()
                    .map(book -> BookDto.builder()
                            .genre(book.getGenre().getName())
                            .name(book.getName())
                            .id(book.getId())
                            .build())
                    .toList();
        }

        AuthorDto authorDto = AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .books(bookDtoList)
                .build();
        return authorDto;
    }

    @Override
    public AuthorDto updateAuthor(AuthorUpdateDto authorUpdateDto) {
        log.info("Update author: {}", authorUpdateDto.toString());
        Author author = authorRepository.findById(authorUpdateDto.getId()).orElseThrow(() -> {
            log.error("Author with id {} not found", authorUpdateDto.getId());
            return new NoSuchElementException("No value present");
        });
        author.setName(authorUpdateDto.getName());
        author.setSurname(authorUpdateDto.getSurname());
        Author savedAuthor = authorRepository.save(author);
        AuthorDto authorDto = convertEntityToDto(savedAuthor);
        log.info("Author updated: {}", authorDto.toString());
        return authorDto;
//        Author author = authorRepository.findById(authorUpdateDto.getId()).orElseThrow();
//        author.setName(authorUpdateDto.getName());
//        author.setSurname(authorUpdateDto.getSurname());
//        Author savedAuthor = authorRepository.save(author);
//        AuthorDto authorDto = convertEntityToDto(savedAuthor);
//        return authorDto;
    }

    @Override
    public void deleteAuthor(Long id) {
        log.info("Delete author with id: {}", id);
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            authorRepository.deleteById(id);
            log.info("Author with id {} was deleted", id);
        } else {
            log.error("An error acquired while deleting author with id {}", id);
            throw new NoSuchElementException("No value present");
        }
//        authorRepository.deleteById(id);
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        log.info("Get all authors");
        List<Author> authors = authorRepository.findAll();
        List<AuthorDto> authorDtos = authors.stream()
                .map(this:: convertEntityToDto)
                .collect(Collectors.toList());
        log.info("Found {} authors", authorDtos.size());
        return authorDtos;
//        List<Author> authors = authorRepository.findAll();
//        return authors.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }
}