package ru.babushkina.libraryproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.babushkina.libraryproject.dto.BookCreateDto;
import ru.babushkina.libraryproject.dto.BookDto;
import ru.babushkina.libraryproject.dto.BookUpdateDto;
import ru.babushkina.libraryproject.model.Book;
import ru.babushkina.libraryproject.model.Genre;
import ru.babushkina.libraryproject.repository.BookRepository;
import ru.babushkina.libraryproject.service.BookService;

import java.nio.charset.Charset;

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

//    @Test
//    public void testUpdateBook() throws Exception {
//        BookUpdateDto bookUpdateDto = new BookUpdateDto();
//        bookUpdateDto.setId(3L);
//        bookUpdateDto.setName("Нос");
//
//        ResponseEntity<BookDto> responseEntity = restTemplate.exchange("/book/update", HttpMethod.PUT,
//                new HttpEntity<>(bookUpdateDto, new HttpHeaders()), BookDto.class);
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//
//        BookDto updatedBookDto = responseEntity.getBody();
//
//        assertEquals(bookUpdateDto.getId(), updatedBookDto.getId());
//        assertEquals(bookUpdateDto.getName(), updatedBookDto.getName());
//    }

    @Test
    public void testDeleteBook() throws Exception {
        Book book = new Book();
        Genre genre = new Genre();
        genre.setId(1L);
        book.setId(3L);
        book.setName("Нос");
        book.setGenre(genre);
        bookRepository.save(book);

        Long id = book.getId();
        mockMvc.perform(MockMvcRequestBuilders.delete("/book/delete/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertFalse(bookRepository.existsById(id));
    }
}
