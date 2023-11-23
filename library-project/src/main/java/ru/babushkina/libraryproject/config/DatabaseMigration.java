//package ru.babushkina.libraryproject.config;
//
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//@Component
//public class DatabaseMigration {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @PostConstruct
//    public void migrate() {
//        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users (" +
//                "id SERIAL PRIMARY KEY," +
//                "username VARCHAR(100) NOT NULL," +
//                "password VARCHAR(100) NOT NULL," +
//                "roles VARCHAR(100) NOT NULL" +
//                ")");
//    }
//}
