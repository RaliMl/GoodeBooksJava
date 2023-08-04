package com.bookstore.demo.domain.authors.repository;

import com.bookstore.demo.domain.authors.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByIdIn(List<Long> authorIds);
}
