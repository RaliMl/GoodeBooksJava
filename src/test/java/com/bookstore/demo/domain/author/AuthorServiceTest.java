package com.bookstore.demo.domain.author;

import com.bookstore.demo.domain.authors.entity.Author;
import com.bookstore.demo.domain.authors.model.AuthorCreateDTO;
import com.bookstore.demo.domain.authors.model.AuthorGetDTO;
import com.bookstore.demo.domain.authors.repository.AuthorRepository;
import com.bookstore.demo.domain.authors.service.AuthorServiceImpl;
import com.bookstore.demo.domain.books.entity.Book;
import com.bookstore.demo.domain.books.repository.BookRepository;
import com.bookstore.demo.exceptions.EntityNotFoundException;
import com.bookstore.demo.infrastructure.mapper.AuthorMapper;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorMapper authorMapper;
    @InjectMocks
    private AuthorServiceImpl authorService;

    private Author author;
    private AuthorCreateDTO authorCreateDTO;
    private AuthorGetDTO authorGetDTO;
    private List<Long> bookIds;
    private List<Book> books;

    @BeforeEach
    public void Init() {
        author = new Author(1L, "Terry Fallis");
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        books = new ArrayList<>();
        books.add(new Book(1L, "Albatross", LocalDate.of(2019, 10, 10), 300, "English", authors));
        bookIds  = new ArrayList<>();
        bookIds.add(1L);
        authorCreateDTO = new AuthorCreateDTO("Terry Fallis", bookIds);
        authorGetDTO = new AuthorGetDTO(1L, "Terry Fallis", bookIds);
    }

    @Test
    void givenValidAuthor_whenSaving_thenOK() {
        when(authorMapper.authorCreateDTOToAuthor(any(AuthorCreateDTO.class))).thenReturn(author);

        when(bookRepository.findByIdIn(any(List.class))).thenReturn(books);

        when(authorRepository.save(any(Author.class))).thenReturn(author);

        when(authorMapper.authorToAuthorGetDTO(any(Author.class))).thenReturn(authorGetDTO);

        AuthorGetDTO created = authorService.create(authorCreateDTO);
        assertThat(created.name(), is(authorGetDTO.name()));
        verify(authorRepository).save(authorMapper.authorCreateDTOToAuthor(authorCreateDTO));
    }
    @Test
    void givenValidID_whenGet_thenOK() {
        Long id = 1L;

        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));

        when(authorMapper.authorToAuthorGetDTO(any(Author.class))).thenReturn(authorGetDTO);

        AuthorGetDTO found = authorService.getById(id);
        assertThat(found.name(), is(author.getName()));
        verify(authorRepository).findById(id);
    }
    @Test
    void whenGetAll_thenOK() {
        Pageable pageable = PageRequest.of(0, 20);
        List<Author> authorsList = new ArrayList<>();
        authorsList.add(author);
        Page<Author> page = new PageImpl<>(authorsList);

        when(authorRepository.findAll(any(Pageable.class))).thenReturn(page);

        when(authorMapper.authorToAuthorGetDTO(any(Author.class))).thenReturn(authorGetDTO);

        Page<AuthorGetDTO> res = authorService.get(pageable);
        List<AuthorGetDTO> list = res.getContent();

        assertThat(list.get(0).name(), is(author.getName()));
    }
    @Test
    void givenValidAuthor_whenUpdating_thenOK() {
        Author newBook = new Author(1L, "New Author");
        AuthorCreateDTO newAuthorCreateDTO = new AuthorCreateDTO("New Author", bookIds);

        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));

        doNothing().when(authorMapper).updateAuthorFromDTO(any(AuthorCreateDTO.class), any(Author.class));

        authorService.update(newAuthorCreateDTO, 1L);

        assertThat(newAuthorCreateDTO.name(), is(newBook.getName()));
    }
    @Test
    void givenValidAuthor_whenDeleting_thenOK() {
        Long id = 1L;

        when(authorRepository.existsById(id)).thenReturn(true);

        doNothing().when(authorRepository).deleteById(anyLong());

        authorService.delete(id);
        verify(authorRepository).deleteById(id);
    }
    @Test
    void givenValidAuthor_whenDeleting_thenException() {
        Long id = 1L;

        when(authorRepository.existsById(id)).thenReturn(false);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            authorService.delete(id);
        });
        String expectedMessage = "The author was not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
