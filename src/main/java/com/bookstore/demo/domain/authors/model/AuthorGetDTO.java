package com.bookstore.demo.domain.authors.model;

import java.util.List;

public record AuthorGetDTO(
       Long id,
       String name,
       List<Long> books
) {
}
