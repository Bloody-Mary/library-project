package ru.babushkina.libraryproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "t_role")
public class Role implements GrantedAuthority {
}