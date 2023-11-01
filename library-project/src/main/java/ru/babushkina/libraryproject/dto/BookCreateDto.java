package ru.babushkina.libraryproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.babushkina.libraryproject.model.Genre;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookCreateDto {
    private String name;
    private Genre genre;
}
