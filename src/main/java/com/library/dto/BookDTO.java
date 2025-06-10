package com.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    
    @NotBlank
    @Size(max = 100)
    private String title;
    
    @NotBlank
    @Size(min = 10, max = 13)
    private String isbn;
    
    private Long authorId;
    private String authorName;
    
    @Size(max = 1000)
    private String description;
    
    private String status;
    
    // Adicione getters e setters para authorId
    public Long getAuthorId() {
        return authorId;
    }
    
    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}