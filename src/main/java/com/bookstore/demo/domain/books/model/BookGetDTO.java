package com.bookstore.demo.domain.books.model;
import java.time.LocalDate;
import java.util.List;

public record BookGetDTO(
        Long id,
        String title,
        LocalDate publishedDate,
        int pageCount,
        String language,
        List<Long> authors
) {
}
