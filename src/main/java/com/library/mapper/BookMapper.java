package com.library.mapper;

import com.library.dto.BookDTO;
import com.library.model.Book;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {AuthorMapper.class})
public interface BookMapper {
    BookDTO toDto(Book book);
    Book toEntity(BookDTO bookDTO);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(BookDTO bookDTO, @MappingTarget Book book);
}