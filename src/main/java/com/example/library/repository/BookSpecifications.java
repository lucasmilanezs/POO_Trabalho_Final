package com.example.library.repository;

import com.example.library.model.Book;
import org.springframework.data.jpa.domain.Specification;

public final class BookSpecifications {

    private BookSpecifications() {
    }

    public static Specification<Book> titleContains(String title) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("title")), likePattern(title));
    }

    public static Specification<Book> authorContains(String author) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("author")), likePattern(author));
    }

    public static Specification<Book> categoryContains(String category) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("category")), likePattern(category));
    }

    public static Specification<Book> availabilityEquals(boolean available) {
        return (root, query, cb) -> cb.equal(root.get("available"), available);
    }

    public static Specification<Book> publicationYearFrom(Integer year) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("publicationYear"), year);
    }

    public static Specification<Book> publicationYearTo(Integer year) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("publicationYear"), year);
    }

    private static String likePattern(String value) {
        return "%" + value.toLowerCase() + "%";
    }
}
