package com.bookstore.demo.domain.authors.model;

import java.util.List;

public record AuthorCreateDTO(
        String name,
        List<Long> books
) {
}
