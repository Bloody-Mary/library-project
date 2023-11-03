package ru.babushkina.libraryproject.dto;

import lombok.*;
import ru.babushkina.libraryproject.model.Genre;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookCreateDto {
    private String name;
    private String genre;
    private Long genreId;
    private Long authorId;
}
