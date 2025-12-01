package com.example.library.mapper;

import com.example.library.dto.BookRequest;
import com.example.library.dto.BookResponse;
import com.example.library.model.Book;

public class BookMapper {

    private BookMapper() {
    }

    public static Book toEntity(BookRequest request) {
        Book book = new Book();
        book.setIsbn(request.isbn());
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setCategory(request.category());
        book.setPublicationYear(request.publicationYear());
        book.setSummary(request.summary());
        return book;
    }

    public static void updateEntity(Book book, BookRequest request) {
        book.setIsbn(request.isbn());
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setCategory(request.category());
        book.setPublicationYear(request.publicationYear());
        book.setSummary(request.summary());
    }

    public static BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getCategory(),
                book.getPublicationYear(),
                book.getSummary(),
                book.getAvailable(),
                book.getLastBorrowedAt(),
                book.getLastReturnedAt(),
                book.getCreatedAt(),
                book.getUpdatedAt()
        );
    }
}
