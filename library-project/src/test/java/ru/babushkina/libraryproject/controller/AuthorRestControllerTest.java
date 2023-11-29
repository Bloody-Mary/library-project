package ru.babushkina.libraryproject.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.babushkina.libraryproject.dto.AuthorCreateDto;
import ru.babushkina.libraryproject.dto.AuthorDto;
import ru.babushkina.libraryproject.service.AuthorService;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class AuthorRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

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
        // Arrange
        AuthorCreateDto authorCreateDto = new AuthorCreateDto();
        authorCreateDto.setName("John");
        authorCreateDto.setSurname("Doe");

        AuthorDto expectedAuthorDto = new AuthorDto();
        expectedAuthorDto.setName(authorCreateDto.getName());
        expectedAuthorDto.setSurname(authorCreateDto.getSurname());

        when(authorService.createAuthor(authorCreateDto)).thenReturn(expectedAuthorDto);

        // Act & Assert
        mockMvc.perform(post("/author/create")
                        .contentType("application/json")
                        .content("{\"name\":\"John\",\"surname\":\"Doe\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(authorCreateDto.getName()))
                .andExpect(jsonPath("$.surname").value(authorCreateDto.getSurname()));
    }
}
