package ru.babushkina.libraryproject.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.babushkina.libraryproject.model.User;

import java.util.Arrays;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(User user) {
        jdbcTemplate.update("INSERT INTO users (username, password, roles) VALUES (?, ?, ?)",
                user.getUsername(), user.getPassword(), String.join(",", user.getRoles()));
    }

    public User findByUsername(String username) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE username = ?",
                new Object[]{username},
                (resultSet, rowNum) -> {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRoles(Arrays.asList(resultSet.getString("roles").split(",")));
                    return user;
                });
    }
}
