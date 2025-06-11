package com.library.service;

import com.library.model.BookStatus; 
import com.library.dto.BookDTO;
import com.library.exception.ResourceNotFoundException;
import com.library.mapper.BookMapper;
import com.library.model.Author;
import com.library.model.Book;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookMapper = bookMapper;
    }

    public Page<BookDTO> getAllBooks(Pageable pageable) {
        log.info("Fetching all books");
        return bookRepository.findAll(pageable)
                .map(bookMapper::toDto);
    }

    public BookDTO getBookById(Long id) {
        log.info("Fetching book with id {}", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return bookMapper.toDto(book);
    }

    @Transactional
    public BookDTO createBook(BookDTO bookDTO) {
        log.info("Creating book: {}", bookDTO);
        Book book = bookMapper.toEntity(bookDTO);
        Author author = authorRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + bookDTO.getAuthorId()));
        book.setAuthor(author);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    @Transactional
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        log.info("Updating book with id {}", id);
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setIsbn(bookDTO.getIsbn());
        BookStatus status = BookStatus.valueOf(bookDTO.getStatus().toUpperCase());
        existingBook.setStatus(status);

        Author author = authorRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + bookDTO.getAuthorId()));
        existingBook.setAuthor(author);

        Book updatedBook = bookRepository.save(existingBook);
        return bookMapper.toDto(updatedBook);
    }

    @Transactional
    public void deleteBook(Long id) {
        log.info("Deleting book with id {}", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        bookRepository.delete(book);
    }
}
