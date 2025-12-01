package com.example.library.controller;

import com.example.library.dto.BookRequest;
import com.example.library.dto.BookResponse;
import com.example.library.service.BookService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest request) {
        BookResponse response = bookService.create(request);
        URI location = URI.create("/api/books/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> update(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
        return ResponseEntity.ok(bookService.update(id, request));
    }

    @GetMapping
    public ResponseEntity<Page<BookResponse>> search(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) Integer yearFrom,
            @RequestParam(required = false) Integer yearTo,
            @PageableDefault(sort = "updatedAt") Pageable pageable
    ) {
        return ResponseEntity.ok(bookService.search(author, category, title, available, yearFrom, yearTo, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @PatchMapping("/{id}/checkout")
    public ResponseEntity<BookResponse> checkout(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.checkout(id));
    }

    @PatchMapping("/{id}/return")
    public ResponseEntity<BookResponse> giveBack(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.giveBack(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
