package com.bookstore.demo.domain.authors.service;

import com.bookstore.demo.domain.authors.model.AuthorCreateDTO;
import com.bookstore.demo.domain.authors.model.AuthorGetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface AuthorService {
    AuthorGetDTO create(AuthorCreateDTO authorDTO);
    AuthorGetDTO getById(Long id) throws Exception;

    Page<AuthorGetDTO> get(Pageable pageable);

    AuthorGetDTO update(AuthorCreateDTO authorDTO, Long id);

    void delete(Long id);
}
