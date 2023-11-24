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
    @Size(min = 1, max = 25)
    @NotBlank(message = "Необходимо указать название")
    private String name;
    @NotBlank(message = "Необходимо указать жанр")
    private String genre;
    @NotBlank(message = "Необходимо указать номер id жанра")
    private Long genreId;
    @NotBlank(message = "Необходимо указать номер id автора")
    private Long authorId;
}
