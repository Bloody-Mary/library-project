package ru.babushkina.libraryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.babushkina.libraryproject.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
