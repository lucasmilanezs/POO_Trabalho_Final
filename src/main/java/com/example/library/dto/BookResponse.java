package com.example.library.dto;

import java.time.LocalDateTime;

public record BookResponse(
        Long id,
        String title,
        String author,
        String category,
        Integer publicationYear,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
