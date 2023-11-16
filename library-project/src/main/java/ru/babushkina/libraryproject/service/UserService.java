package ru.babushkina.libraryproject.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.security.core.userdetails.UserDetailsService;

public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
}
