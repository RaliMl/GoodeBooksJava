package com.bookstore.demo.domain.books.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

public record BookCreateDTO(
        @NotBlank
        String title,
        @NotBlank

        LocalDate publishedDate,
        @NotBlank
        @Positive
        int pageCount,
        @NotBlank
        String language,
        @NotBlank
        List<String> authors
) {
}
