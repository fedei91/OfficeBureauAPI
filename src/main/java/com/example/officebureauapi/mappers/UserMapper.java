package com.example.officebureauapi.mappers;

import com.example.officebureauapi.dto.UserDto;
import com.example.officebureauapi.entities.User;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    UserDto toDto(User user, @MappingTarget UserDto userDto);

    @Mapping(target = "id", ignore = true)
    User toEntity(UserDto userDto, @MappingTarget User user);
}
