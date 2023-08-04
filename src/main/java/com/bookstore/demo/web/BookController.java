package com.bookstore.demo.web;

import com.bookstore.demo.domain.books.model.BookCreateDTO;
import com.bookstore.demo.domain.books.model.BookGetDTO;
import com.bookstore.demo.domain.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookGetDTO> create (@RequestBody BookCreateDTO bookDTO) {
        return ResponseEntity.ok(bookService.create(bookDTO));
    }

    @GetMapping
    @PreAuthorize("hastAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    public ResponseEntity<Page<BookGetDTO>> get(Pageable pageable) {
        return  ResponseEntity.ok(bookService.get(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookGetDTO> getById(@PathVariable Long id) throws Exception {

        BookGetDTO task = bookService.getById(id);
        return ResponseEntity.ok(task);

    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookCreateDTO> update(@RequestBody BookCreateDTO account, @PathVariable Long id) {

        bookService.update(account, id);
        return ResponseEntity.ok(account);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
