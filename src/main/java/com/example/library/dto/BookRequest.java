package com.example.library.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookRequest(
        @NotBlank(message = "O título é obrigatório")
        String title,
        @NotBlank(message = "O autor é obrigatório")
        String author,
        @NotBlank(message = "A categoria é obrigatória")
        String category,
        @NotNull(message = "O ano de publicação é obrigatório")
        @Min(value = 1400, message = "O ano deve ser plausível")
        Integer publicationYear
) {
}
