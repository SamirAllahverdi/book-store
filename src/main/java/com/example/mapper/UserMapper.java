package com.example.mapper;

import com.example.domain.Role;
import com.example.domain.User;
import com.example.domain.enumeration.UserRole;
import com.example.dto.RegistrationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    @Mappings({
            @Mapping(target = "password", source = "encodedPassword"),
            @Mapping(target = "roles", source = "request.role", qualifiedByName = "toAuthorities")
    })
    User toEntity(RegistrationRequest request, String encodedPassword);

    @Named("toAuthorities")
    default Set<Role> toAuthorities(UserRole role) {
        return Set.of(new Role(role.getId()));
    }
}
