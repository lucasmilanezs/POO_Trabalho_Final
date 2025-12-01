package com.example.library.repository;

import com.example.library.model.Book;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    boolean existsByIsbn(String isbn);

    Optional<Book> findByIsbn(String isbn);
}
