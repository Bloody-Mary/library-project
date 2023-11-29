package ru.babushkina.libraryproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.babushkina.libraryproject.dto.AuthorCreateDto;
import ru.babushkina.libraryproject.dto.AuthorDto;
import ru.babushkina.libraryproject.dto.AuthorUpdateDto;
import ru.babushkina.libraryproject.model.Author;
import ru.babushkina.libraryproject.model.Book;
import ru.babushkina.libraryproject.repository.AuthorRepository;
import ru.babushkina.libraryproject.repository.BookRepository;
import ru.babushkina.libraryproject.service.AuthorService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class AuthorRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAuthorById() throws Exception {
        Long authorId = 1L;
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(authorId);
        when(authorService.getAuthorById(authorId)).thenReturn(authorDto);

        mockMvc.perform(get("/author/{id}", authorId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(authorDto.getId()));
    }

    @Test
    public void testGetAuthorByName() throws Exception {
        String authorName = "Николай";
        AuthorDto expectedAuthorDto = new AuthorDto();
        expectedAuthorDto.setName(authorName);

        when(authorService.getByNameV1(authorName)).thenReturn(expectedAuthorDto);

        mockMvc.perform(get("/author")
                        .param("name", authorName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(authorName));
    }

    @Test
    public void testGetAuthorNameV2() throws Exception {
        String authorName = "Федор";
        AuthorDto expectedAuthorDto = new AuthorDto();
        expectedAuthorDto.setName(authorName);

        when(authorService.getByNameV2(authorName)).thenReturn(expectedAuthorDto);

        mockMvc.perform(get("/author/v2")
                .param("name", authorName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(authorName));
    }

    @Test
    public void testGetAuthorByNameV3() throws Exception {
        String authorName = "Михаил";
        AuthorDto expectedAuthorDto = new AuthorDto();
        expectedAuthorDto.setName(authorName);

        when(authorService.getByNameV3(authorName)).thenReturn(expectedAuthorDto);

        mockMvc.perform(get("/author/v3")
                .param("name", authorName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(authorName));
    }

    @Test
    public void testCreateAuthor() throws Exception {
        AuthorCreateDto authorCreateDto = new AuthorCreateDto();
        authorCreateDto.setName("John");
        authorCreateDto.setSurname("Doe");

        AuthorDto expectedAuthorDto = new AuthorDto();
        expectedAuthorDto.setName(authorCreateDto.getName());
        expectedAuthorDto.setSurname(authorCreateDto.getSurname());

        when(authorService.createAuthor(authorCreateDto)).thenReturn(expectedAuthorDto);

        mockMvc.perform(post("/author/create")
                        .contentType("application/json")
                        .content("{\"name\":\"John\",\"surname\":\"Doe\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(authorCreateDto.getName()))
                .andExpect(jsonPath("$.surname").value(authorCreateDto.getSurname()));
    }

    @Test
    public void testUpdateAuthor() throws Exception {
        AuthorUpdateDto authorUpdateDto = new AuthorUpdateDto();
        authorUpdateDto.setId(1L);
        authorUpdateDto.setName("John");
        authorUpdateDto.setSurname("Doe");
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/author/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(authorUpdateDto));
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        AuthorDto updatedAuthorDto = new ObjectMapper().readValue(responseBody, AuthorDto.class);

        assertEquals(authorUpdateDto.getId(), updatedAuthorDto.getId());
        assertEquals(authorUpdateDto.getName(), updatedAuthorDto.getName());
        assertEquals(authorUpdateDto.getSurname(), updatedAuthorDto.getSurname());
    }

    @Test
    public void testDeleteAuthor() throws Exception {
        Author author = new Author();
        author.setId(11L);
        author.setName("John");
        author.setSurname("Doe");
        author = authorRepository.save(author);

        Long authorId = author.getId();
        mockMvc.perform(delete("/author/delete/{id}", authorId))
                .andExpect(status().isOk());

        assertFalse(authorRepository.existsById(authorId));
    }
}
