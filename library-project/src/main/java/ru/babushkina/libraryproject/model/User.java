package ru.babushkina.libraryproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "t_user")
public class User implements UserDetails {

}
