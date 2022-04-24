package com.example.mapper;

import com.example.domain.User;
import com.example.dto.RegistrationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "password",source = "encodedPassword")
    User toEntity(RegistrationRequest request,String encodedPassword);
}
