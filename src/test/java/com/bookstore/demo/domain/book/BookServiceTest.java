package com.bookstore.demo.domain.book;

import com.bookstore.demo.domain.authors.entity.Author;
import com.bookstore.demo.domain.books.entity.Book;
import com.bookstore.demo.domain.books.model.BookCreateDTO;
import com.bookstore.demo.domain.books.model.BookGetDTO;
import com.bookstore.demo.domain.books.repository.BookRepository;
import com.bookstore.demo.domain.books.service.BookServiceImpl;
import com.bookstore.demo.infrastructure.mapper.BookMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;
    private BookGetDTO bookGetDTO;
    private BookCreateDTO bookCreateDTO;
    private List<Author> authors;

    private List<Long> authorIds;
    @BeforeEach
    public void Init() {
        book = new Book(1L, "Albatross", LocalDate.of(2019, 10, 10), 300, "English", authors);
        authorIds = new ArrayList<>();
        authorIds.add(1L);
        bookCreateDTO = new BookCreateDTO("Albatross", LocalDate.of(2019, 10, 10), 300, "English", authorIds);
        bookGetDTO = new BookGetDTO(1L, "Albatross", LocalDate.of(2019, 10, 10), 300, "English", authorIds);
    }
    @Test
    void givenValidBook_whenSaving_thenOK() {
        when(bookMapper.bookCreateDTOToBook(any(BookCreateDTO.class))).thenReturn(book);

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        when(bookMapper.bookToBookgetDTO(any(Book.class))).thenReturn(bookGetDTO);

        BookGetDTO created = bookService.create(bookCreateDTO);
        assertThat(created.title(), is(bookGetDTO.title()));
        verify(bookRepository).save(bookMapper.bookCreateDTOToBook(bookCreateDTO));
    }

    @Test
    void givenValidID_whenGet_thenOK() {
        Long id = 1L;

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        when(bookMapper.bookToBookgetDTO(any(Book.class))).thenReturn(bookGetDTO);

        BookGetDTO found = bookService.getById(id);
        assertThat(found.title(), is(book.getTitle()));
        verify(bookRepository).findById(id);
    }
    @Test
    void givenValidID_whenGetAll_thenOK() {
        Pageable pageable = PageRequest.of(0, 20);
        List<Book> books = new ArrayList<>();
        books.add(book);
        Page<Book> page = new PageImpl<>(books);

        when(bookRepository.findAll(any(Pageable.class))).thenReturn(page);

        when(bookMapper.bookToBookgetDTO(any(Book.class))).thenReturn(bookGetDTO);

        Page<BookGetDTO> res = bookService.get(pageable);
        List<BookGetDTO> list = res.getContent();

        assertThat(list.get(0).title(), is(book.getTitle()));
    }
    @Test
    void givenValidAccount_whenUpdating_thenOK() {
        Book newBook = new Book(1L, "Albatross", LocalDate.of(2018, 10, 10), 300, "English", authors);
        BookCreateDTO newBookCreateDTO = new BookCreateDTO("Albatross", LocalDate.of(2018, 10, 10), 300, "English", authorIds);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        doNothing().when(bookMapper).updateBookFromDTO(any(BookCreateDTO.class), any(Book.class));

        bookService.update(newBookCreateDTO, 1L);

        assertThat(newBookCreateDTO.publishedDate(), is(newBook.getPublishedDate()));
    }
    @Test
    void givenValidBook_whenDeleting_thenOK() {
        Long id = 1L;

        when(bookRepository.existsById(id)).thenReturn(true);

        doNothing().when(bookRepository).deleteById(anyLong());

        bookService.delete(id);
        verify(bookRepository).deleteById(id);
    }
    @Test
    void givenValidBook_whenDeleting_thenException() {
        Long id = 1L;

        when(bookRepository.existsById(id)).thenReturn(false);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            bookService.delete(id);
        });
        String expectedMessage = "The book was not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
