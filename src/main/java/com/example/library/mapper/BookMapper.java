package com.example.library.mapper;

import com.example.library.dto.BookRequest;
import com.example.library.dto.BookResponse;
import com.example.library.model.Book;
import java.time.LocalDateTime;

public class BookMapper {

    private BookMapper() {
    }

    public static Book toEntity(BookRequest request) {
        LocalDateTime now = LocalDateTime.now();
        Book book = new Book();
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setCategory(request.category());
        book.setPublicationYear(request.publicationYear());
        book.setCreatedAt(now);
        book.setUpdatedAt(now);
        return book;
    }

    public static void updateEntity(Book book, BookRequest request) {
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setCategory(request.category());
        book.setPublicationYear(request.publicationYear());
        book.setUpdatedAt(LocalDateTime.now());
    }

    public static BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getCategory(),
                book.getPublicationYear(),
                book.getCreatedAt(),
                book.getUpdatedAt()
        );
    }
}
