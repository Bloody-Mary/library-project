package ru.babushkina.libraryproject.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.babushkina.libraryproject.dto.BookCreateDto;
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

    @Test
    public void testGetBookByNameV2() throws Exception {
        String bookName = "Сага о Форсайтах";
        BookDto expectedBookDto = new BookDto();
        expectedBookDto.setName(bookName);

        when(bookService.getByNameV2(bookName)).thenReturn(expectedBookDto);

        mockMvc.perform(get("/book/v2")
                .param("name", bookName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(bookName));
    }

    @Test
    public void testGetBookByNameV3() throws Exception {
        String bookName = "Хроники странствующего кота";
        BookDto expectedBookDto = new BookDto();
        expectedBookDto.setName(bookName);

        when(bookService.getByNameV3(bookName)).thenReturn(expectedBookDto);

        mockMvc.perform(get("/book/v3")
                .param("name", bookName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(bookName));
    }

    @Test
    public void testCreateBook() throws Exception {
        BookCreateDto bookCreateDto = new BookCreateDto();
        bookCreateDto.setAuthorId(1L);
        bookCreateDto.setName("Повести Белкина");
        bookCreateDto.setGenre("Повесть");

        BookDto expectedBookDto = new BookDto();
        expectedBookDto.setId(1L);
        expectedBookDto.setName(bookCreateDto.getName());
        expectedBookDto.setGenre(bookCreateDto.getGenre());

        when(bookService.createBook(bookCreateDto)).thenReturn(expectedBookDto);

        mockMvc.perform(post("/book/create")
                        .contentType("application/json")
                        .content("{\"name\":\"Повести Белкина\",\"genre\":\"Повесть\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedBookDto.getId()))
                .andExpect(jsonPath("$.name").value(expectedBookDto.getName()))
                .andExpect(jsonPath("$.genre").value(expectedBookDto.getGenre()));
    }
}
