package com.library.mapper;

import com.library.dto.AuthorDTO;
import com.library.model.AuthorModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDTO toDto(AuthorModel author);
    AuthorModel toEntity(AuthorDTO authorDTO);
}