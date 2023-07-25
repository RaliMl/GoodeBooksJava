package com.bookstore.demo.domain.books.service;

import com.bookstore.demo.domain.books.entity.Book;
import com.bookstore.demo.domain.books.model.BookCreateDTO;
import com.bookstore.demo.domain.books.model.BookGetDTO;
import com.bookstore.demo.domain.books.repository.BookRepository;
import com.bookstore.demo.infrastructure.mapper.BookMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class BookServiceImpl implements BookService{
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    public BookServiceImpl(BookMapper bookMapper, BookRepository bookRepository) {
        this.bookMapper = bookMapper;
        this.bookRepository = bookRepository;
    }

    private Book findBookById(Long id) {

        return bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The book was not found!"));
    }
    @Override
    public BookGetDTO create(BookCreateDTO bookDTO) {

        Book book = bookMapper.bookCreateDTOToBook(bookDTO);

        Book createdBook = bookRepository.save(book);

        return bookMapper.bookToBookgetDTO(book);
    }

    @Override
    public BookGetDTO getById(Long id) throws Exception {

        return bookMapper.bookToBookgetDTO(findBookById(id));
    }

    @Override
    public Page<BookGetDTO> get(Pageable pageable) {

        Page<Book> page = bookRepository.findAll(pageable);

        return page.map(entity -> {
            BookGetDTO dto = bookMapper.bookToBookgetDTO(entity); return dto;
        });
    }

    @Override
    public BookGetDTO update(BookCreateDTO bookDTO, Long id) {

        Book currentBook = findBookById(id);

        bookMapper.updateBookFromDTO(bookDTO, currentBook);
        bookRepository.save(currentBook);

        return bookMapper.bookToBookgetDTO(currentBook);
    }

    @Override
    public void delete(Long id) {

        if(!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("The book was not found!");
        }

        bookRepository.deleteById(id);
    }
}
