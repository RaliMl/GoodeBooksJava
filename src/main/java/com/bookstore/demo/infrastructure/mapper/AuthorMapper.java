package com.bookstore.demo.infrastructure.mapper;

import com.bookstore.demo.domain.authors.entity.Author;
import com.bookstore.demo.domain.authors.model.AuthorCreateDTO;
import com.bookstore.demo.domain.authors.model.AuthorGetDTO;
import com.bookstore.demo.domain.books.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    @Mapping(target="books", ignore = true)
    Author authorCreateDTOToAuthor(AuthorCreateDTO authorDTO);
    @Mapping(source="books", target="books")
    AuthorGetDTO authorToAuthorGetDTO(Author author);
    @Mapping(target="books", ignore = true)
    void updateAuthorFromDTO(AuthorCreateDTO bookDTO, @MappingTarget Author entity);
    default List<Long> mapBooksToBookIds(List<Book> books) {
        if (books == null) {
            return null;
        }
        return books.stream()
                .map(Book::getId)
                .collect(Collectors.toList());
    }

}
