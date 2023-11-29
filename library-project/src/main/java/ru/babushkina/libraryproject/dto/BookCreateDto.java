package ru.babushkina.libraryproject.dto;

import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
public class BookCreateDto {
    @Size(min = 1, max = 25)
    @NotBlank(message = "Необходимо указать название")
    private String name;
    private String genre;
    private Long genreId;
    private Long authorId;
}
