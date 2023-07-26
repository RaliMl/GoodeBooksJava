package com.bookstore.demo.domain.authors.service;

import com.bookstore.demo.domain.authors.entity.Author;
import com.bookstore.demo.domain.authors.model.AuthorCreateDTO;
import com.bookstore.demo.domain.authors.model.AuthorGetDTO;
import com.bookstore.demo.domain.authors.repository.AuthorRepository;
import com.bookstore.demo.domain.books.entity.Book;
import com.bookstore.demo.domain.books.repository.BookRepository;
import com.bookstore.demo.exceptions.EntityNotFoundException;
import com.bookstore.demo.infrastructure.mapper.AuthorMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorMapper authorMapper;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorServiceImpl(AuthorMapper authorMapper, AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorMapper = authorMapper;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    private Author findAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The author was not found!"));
    }
    @Override
    public AuthorGetDTO create(AuthorCreateDTO authorDTO) {
        Author author = authorMapper.authorCreateDTOToAuthor(authorDTO);
        List<Book> books = bookRepository.findBooksByIds(authorDTO.books());
        author.setBooks(books);

        authorRepository.save(author);

        return authorMapper.authorToAuthorGetDTO(author);

    }

    @Override
    public AuthorGetDTO getById(Long id) throws Exception {
        return authorMapper.authorToAuthorGetDTO(findAuthorById(id));
    }

    @Override
    public Page<AuthorGetDTO> get(Pageable pageable) {
        Page<Author> page = authorRepository.findAll(pageable);

        return page.map(entity -> {
            AuthorGetDTO dto = authorMapper.authorToAuthorGetDTO(entity); return dto;
        });
    }

    @Override
    public AuthorGetDTO update(AuthorCreateDTO authorDTO, Long id) {
        Author currentAuthor = findAuthorById(id);
        authorMapper.updateAuthorFromDTO(authorDTO, currentAuthor);

        List<Book> books = bookRepository.findBooksByIds(authorDTO.books());
        currentAuthor.setBooks(books);

        authorRepository.save(currentAuthor);
        return authorMapper.authorToAuthorGetDTO(currentAuthor);
    }

    @Override
    public void delete(Long id) {
        if(!authorRepository.existsById(id))
            throw new EntityNotFoundException("The author was not found!");

        authorRepository.deleteById(id);
    }
}
