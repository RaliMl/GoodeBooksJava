package com.bookstore.demo.web;

import com.bookstore.demo.domain.authors.model.AuthorCreateDTO;
import com.bookstore.demo.domain.authors.model.AuthorGetDTO;
import com.bookstore.demo.domain.authors.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/author")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AuthorGetDTO> create (@RequestBody AuthorCreateDTO authorDTO) {
        return ResponseEntity.ok(authorService.create(authorDTO));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    public ResponseEntity<Page<AuthorGetDTO>> get(Pageable pageable) {
        return  ResponseEntity.ok(authorService.get(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AuthorGetDTO> getById(@PathVariable Long id) throws Exception {

        AuthorGetDTO task = authorService.getById(id);
        return ResponseEntity.ok(task);

    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AuthorCreateDTO> update(@RequestBody AuthorCreateDTO authorDTO, @PathVariable Long id) {

        authorService.update(authorDTO, id);
        return ResponseEntity.ok(authorDTO);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
