package com.example.mapper;

import com.example.domain.Book;
import com.example.dto.BookRequest;
import com.example.dto.BookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookResponse toDto(Book book);

//    @Mapping(target = "author.id", source = "authorId")
    Book toEntity(BookRequest dto);

}
