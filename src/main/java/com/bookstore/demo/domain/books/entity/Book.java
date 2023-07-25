package com.bookstore.demo.domain.books.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private LocalDate publishedDate;
    @Column(nullable = false)
    private int pageCount;
    @Column(nullable = false)
    private String language;

    public Book(Long id, String title, LocalDate publishedDate, int pageCount, String language) {
        this.id = id;
        this.title = title;
        this.publishedDate = publishedDate;
        this.pageCount = pageCount;
        this.language = language;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }
    public int getPageCount() {
        return pageCount;
    }
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
    public String getLanguage() {
       return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
}
