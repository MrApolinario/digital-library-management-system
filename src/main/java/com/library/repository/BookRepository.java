package com.library.repository;

import com.library.model.BookModel;
import com.library.model.BookStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookModel, Long> {
    Optional<BookModel> findByIsbn(String isbn);
    List<BookModel> findByAuthorId(Long authorId);
    List<BookModel> findByStatus(BookStatus status);
    Page<BookModel> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}