package com.example.officebureauapi.mappers.entitytodto;

import com.example.officebureauapi.dto.UserDto;
import com.example.officebureauapi.entities.User;
import com.example.officebureauapi.mappers.EntityToDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserEntityToDtoMapper implements EntityToDtoMapper<User, UserDto> {
    @Override
    public UserDto toDto(User entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getId().toString());
        dto.setEmail(entity.getEmail());
        return dto;
    }

    @Override
    public User toEntity(UserDto dto, User entity) {
        entity.setId(UUID.fromString(dto.getId()));
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setDeleted(dto.isDeleted());
        return entity;
    }
}
