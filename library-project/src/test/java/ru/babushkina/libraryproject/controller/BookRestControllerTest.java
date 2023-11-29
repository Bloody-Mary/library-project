package ru.babushkina.libraryproject.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.babushkina.libraryproject.dto.BookDto;
import ru.babushkina.libraryproject.repository.BookRepository;
import ru.babushkina.libraryproject.service.BookService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class BookRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testGetBookByName() throws Exception {
        String bookName = "Телеграмма";
        BookDto expectedBookDto = new BookDto();
        expectedBookDto.setName(bookName);

        when(bookService.getByNameV1(bookName)).thenReturn(expectedBookDto);

        mockMvc.perform(get("/book")
                .param("name", bookName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(bookName));
    }
}
