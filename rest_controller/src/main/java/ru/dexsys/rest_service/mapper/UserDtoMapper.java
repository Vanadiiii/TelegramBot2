package ru.dexsys.rest_service.mapper;

import org.mapstruct.Mapper;
import ru.dexsys.domain.entity.UserEntity;
import ru.dexsys.rest_service.dto.UserDto;

import java.util.function.Function;

@Mapper(componentModel = "spring")
public interface UserDtoMapper extends Function<UserEntity, UserDto> {
}
