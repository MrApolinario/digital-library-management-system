package com.library.service.impl;

import com.library.dto.AuthorDTO;
import com.library.model.AuthorModel;
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
                .map(authorModel -> modelMapper.map(authorModel, AuthorDTO.class));
    }

    @Override
    public AuthorDTO getAuthorById(Long id) {
        AuthorModel authorModel = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        return modelMapper.map(authorModel, AuthorDTO.class);
    }

    @Override
    public AuthorDTO createAuthor(AuthorDTO dto) {
        AuthorModel authorModel = modelMapper.map(dto, AuthorModel.class);
        AuthorModel saved = authorRepository.save(authorModel);
        return modelMapper.map(saved, AuthorDTO.class);
    }

    @Override
    public AuthorDTO updateAuthor(Long id, AuthorDTO dto) {
        AuthorModel existing = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        existing.setName(dto.getName());
        existing.setBiography(dto.getBiography());
        AuthorModel saved = authorRepository.save(existing);
        return modelMapper.map(saved, AuthorDTO.class);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}

