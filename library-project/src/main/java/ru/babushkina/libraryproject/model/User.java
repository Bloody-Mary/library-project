package ru.babushkina.libraryproject.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.Transient;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "t_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min=2, message = "Не менее 5 знаков")
    private String username;

    @Size(min=2, message="Не менее 5 знаков")
    private String password;

    @Transient
    private String passwordConfirm;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
}
