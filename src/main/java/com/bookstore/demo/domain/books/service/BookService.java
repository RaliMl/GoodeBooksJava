package com.bookstore.demo.domain.books.service;

import com.bookstore.demo.domain.books.model.BookCreateDTO;
import com.bookstore.demo.domain.books.model.BookGetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface BookService {
    BookGetDTO create(BookCreateDTO book);
    BookGetDTO getById(Long id) throws Exception;

    Page<BookGetDTO> get(Pageable pageable);

    BookGetDTO update(BookCreateDTO bookDTO, Long id);

    void delete(Long id);
}
