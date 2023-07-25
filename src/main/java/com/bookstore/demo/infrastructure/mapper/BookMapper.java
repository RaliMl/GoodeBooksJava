package com.bookstore.demo.infrastructure.mapper;

import com.bookstore.demo.domain.books.entity.Book;
import com.bookstore.demo.domain.books.model.BookCreateDTO;
import com.bookstore.demo.domain.books.model.BookGetDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book bookCreateDTOToBook(BookCreateDTO bookDTO);
    BookGetDTO bookToBookgetDTO(Book book);
    void updateBookFromDTO(BookCreateDTO bookDTO, @MappingTarget Book entity);
}
