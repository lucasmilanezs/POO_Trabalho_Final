package com.example.library.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String category;

    private Integer publicationYear;

    @Column(length = 2000)
    private String summary;

    @Column(nullable = false)
    private Boolean available;

    private LocalDateTime lastBorrowedAt;

    private LocalDateTime lastReturnedAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public Book() {
    }

    public Book(Long id, String isbn, String title, String author, String category, Integer publicationYear,
                String summary, Boolean available, LocalDateTime lastBorrowedAt, LocalDateTime lastReturnedAt,
                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
        this.publicationYear = publicationYear;
        this.summary = summary;
        this.available = available;
        this.lastBorrowedAt = lastBorrowedAt;
        this.lastReturnedAt = lastReturnedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    public void prePersist() {
        if (available == null) {
            available = true;
        }
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public LocalDateTime getLastBorrowedAt() {
        return lastBorrowedAt;
    }

    public void setLastBorrowedAt(LocalDateTime lastBorrowedAt) {
        this.lastBorrowedAt = lastBorrowedAt;
    }

    public LocalDateTime getLastReturnedAt() {
        return lastReturnedAt;
    }

    public void setLastReturnedAt(LocalDateTime lastReturnedAt) {
        this.lastReturnedAt = lastReturnedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
