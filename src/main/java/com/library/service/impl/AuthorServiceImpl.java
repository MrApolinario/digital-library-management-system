package com.library.service.impl;

import com.library.dto.AuthorDTO;
// TROQUE este import:
import com.library.entity.Author;  // <-- aqui está sua entidade Author, não AuthorModel
import com.library.repository.AuthorRepository;
import com.library.service.AuthorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<AuthorDTO> getAllAuthors(Pageable pageable) {
        return authorRepository.findAll(pageable)
                .map(author -> modelMapper.map(author, AuthorDTO.class));
    }

    @Override
    public AuthorDTO getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        return modelMapper.map(author, AuthorDTO.class);
    }

    @Override
    public AuthorDTO createAuthor(AuthorDTO dto) {
        Author author = modelMapper.map(dto, Author.class);
        Author saved = authorRepository.save(author);
        return modelMapper.map(saved, AuthorDTO.class);
    }

    @Override
    public AuthorDTO updateAuthor(Long id, AuthorDTO dto) {
        Author existing = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        existing.setName(dto.getName());
        existing.setBiography(dto.getBiography());
        Author saved = authorRepository.save(existing);
        return modelMapper.map(saved, AuthorDTO.class);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
