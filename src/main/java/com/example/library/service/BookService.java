package com.example.library.service;

import com.example.library.dto.BookRequest;
import com.example.library.dto.BookResponse;
import com.example.library.mapper.BookMapper;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.example.library.repository.BookSpecifications;
import java.time.LocalDateTime;
import java.time.Year;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookResponse create(BookRequest request) {
        validateYear(request.publicationYear());
        validateIsbnUniqueness(request.isbn(), null);
        Book book = BookMapper.toEntity(request);
        return BookMapper.toResponse(bookRepository.save(book));
    }

    public BookResponse update(Long id, BookRequest request) {
        Book book = findBookOrThrow(id);
        validateYear(request.publicationYear());
        validateIsbnUniqueness(request.isbn(), id);
        BookMapper.updateEntity(book, request);
        return BookMapper.toResponse(bookRepository.save(book));
    }

    public Page<BookResponse> search(String author, String category, String title, Boolean available,
                                     Integer yearFrom, Integer yearTo, Pageable pageable) {
        Specification<Book> spec = Specification.where(null);

        if (author != null && !author.isBlank()) {
            spec = spec.and(BookSpecifications.authorContains(author));
        }
        if (category != null && !category.isBlank()) {
            spec = spec.and(BookSpecifications.categoryContains(category));
        }
        if (title != null && !title.isBlank()) {
            spec = spec.and(BookSpecifications.titleContains(title));
        }
        if (available != null) {
            spec = spec.and(BookSpecifications.availabilityEquals(available));
        }
        if (yearFrom != null) {
            spec = spec.and(BookSpecifications.publicationYearFrom(yearFrom));
        }
        if (yearTo != null) {
            spec = spec.and(BookSpecifications.publicationYearTo(yearTo));
        }

        return bookRepository.findAll(spec, pageable).map(BookMapper::toResponse);
    }

    public BookResponse findById(Long id) {
        return BookMapper.toResponse(findBookOrThrow(id));
    }

    public void delete(Long id) {
        Book book = findBookOrThrow(id);
        bookRepository.delete(book);
    }

    public BookResponse checkout(Long id) {
        Book book = findBookOrThrow(id);
        if (Boolean.FALSE.equals(book.getAvailable())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Livro já está emprestado e não pode ser retirado novamente");
        }
        book.setAvailable(false);
        book.setLastBorrowedAt(LocalDateTime.now());
        return BookMapper.toResponse(bookRepository.save(book));
    }

    public BookResponse giveBack(Long id) {
        Book book = findBookOrThrow(id);
        if (Boolean.TRUE.equals(book.getAvailable())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Livro já está disponível e não pode ser devolvido");
        }
        book.setAvailable(true);
        book.setLastReturnedAt(LocalDateTime.now());
        return BookMapper.toResponse(bookRepository.save(book));
    }

    private Book findBookOrThrow(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Livro com id %d não encontrado".formatted(id)));
    }

    private void validateIsbnUniqueness(String isbn, Long currentId) {
        bookRepository.findByIsbn(isbn)
                .filter(existing -> currentId == null || !existing.getId().equals(currentId))
                .ifPresent(existing -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT,
                            "Já existe um livro cadastrado com o ISBN informado");
                });
    }

    private void validateYear(Integer publicationYear) {
        if (publicationYear != null && publicationYear > Year.now().getValue()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "O ano de publicação não pode estar no futuro");
        }
    }
}
