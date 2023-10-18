package ru.babushkina.libraryproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.babushkina.libraryproject.repository.AuthorRepository;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService{
    private final AuthorRepository authorRepository;
}
