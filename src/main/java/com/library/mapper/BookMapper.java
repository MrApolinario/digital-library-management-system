package com.library.mapper;

import com.library.dto.BookDTO;
import com.library.model.BookModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {AuthorMapper.class})
public interface BookMapper {
    BookDTO toDto(BookModel book);
    BookModel toEntity(BookDTO bookDTO);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(BookDTO bookDTO, @MappingTarget BookModel book);
}