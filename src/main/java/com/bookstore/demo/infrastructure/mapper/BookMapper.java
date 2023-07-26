package com.bookstore.demo.infrastructure.mapper;

import com.bookstore.demo.domain.authors.entity.Author;
import com.bookstore.demo.domain.books.entity.Book;
import com.bookstore.demo.domain.books.model.BookCreateDTO;
import com.bookstore.demo.domain.books.model.BookGetDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target="authors", ignore = true)
    Book bookCreateDTOToBook(BookCreateDTO bookDTO);
    @Mapping(source="authors", target="authors")
    BookGetDTO bookToBookgetDTO(Book book);
    @Mapping(target="authors", ignore = true)
    void updateBookFromDTO(BookCreateDTO bookDTO, @MappingTarget Book entity);
    default List<Long> mapAuthorsToAuthorIds(List<Author> authors) {
        if (authors == null) {
            return null;
        }
        return authors.stream()
                .map(Author::getId)
                .collect(Collectors.toList());
    }
}
