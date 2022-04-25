package com.example.mapper;


import com.example.domain.Author;
import com.example.domain.User;
import com.example.dto.AuthorRequest;
import com.example.dto.RegistrationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    Author toEntity(AuthorRequest dto);

}
