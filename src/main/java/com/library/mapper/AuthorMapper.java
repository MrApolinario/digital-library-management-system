package com.library.mapper;

import com.library.dto.AuthorDTO;
import com.library.entity.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDTO toDto(Author author);
    Author toEntity(AuthorDTO authorDTO);
}
