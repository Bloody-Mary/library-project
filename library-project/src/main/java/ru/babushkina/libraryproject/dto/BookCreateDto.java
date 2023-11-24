package ru.babushkina.libraryproject.dto;

import lombok.*;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookCreateDto {
    @Size(min = 3, max = 10)
    @NotBlank(message = "Необходимо указать название")
    private String name;
    @NotBlank(message = "Необходимо указать жанр")
    private String genre;
    private Long genreId;
    private Long authorId;
}
