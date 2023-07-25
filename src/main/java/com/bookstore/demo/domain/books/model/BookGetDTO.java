package com.bookstore.demo.domain.books.model;
import java.time.LocalDate;

public record BookGetDTO(
        Long id,
        String title,
        LocalDate publishedDate,
        int pageCount,
        String language
) {
}
