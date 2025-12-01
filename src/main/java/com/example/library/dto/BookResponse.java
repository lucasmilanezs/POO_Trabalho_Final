package com.example.library.dto;

import java.time.LocalDateTime;

public record BookResponse(
        Long id,
        String isbn,
        String title,
        String author,
        String category,
        Integer publicationYear,
        String summary,
        Boolean available,
        LocalDateTime lastBorrowedAt,
        LocalDateTime lastReturnedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
