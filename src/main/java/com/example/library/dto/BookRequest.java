package com.example.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookRequest(
        @NotBlank(message = "O ISBN é obrigatório")
        @Size(max = 20, message = "O ISBN deve ter no máximo 20 caracteres")
        String isbn,
        @NotBlank(message = "O título é obrigatório")
        String title,
        @NotBlank(message = "O autor é obrigatório")
        String author,
        @NotBlank(message = "A categoria é obrigatória")
        String category,
        @NotNull(message = "O ano de publicação é obrigatório")
        Integer publicationYear,
        @Size(max = 2000, message = "O resumo deve ter no máximo 2000 caracteres")
        String summary
) {
}
