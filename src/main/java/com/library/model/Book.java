package com.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotBlank(message = "ISBN cannot be blank")
    @Size(min = 10, max = 13, message = "ISBN must be between 10 and 13 characters")
    private String isbn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private BookStatus status = BookStatus.AVAILABLE; // Valor padrão

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Método auxiliar para verificar disponibilidade
    public boolean isAvailable() {
        return this.status == BookStatus.AVAILABLE;
    }

    // Método para emprestar o livro
    public void borrow() {
        if (!isAvailable()) {
            throw new IllegalStateException("Book is not available for borrowing");
        }
        this.status = BookStatus.BORROWED;
    }

    // Método para devolver o livro
    public void returnBook() {
        this.status = BookStatus.AVAILABLE;
    }
}