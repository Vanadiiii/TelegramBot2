package ru.dexsys.mapper;

import org.mapstruct.Mapper;
import ru.dexsys.domain.entity.UserEntity;
import ru.dexsys.dto.UserDto;

import java.util.function.Function;

@Mapper(componentModel = "spring")
public interface UserDataDtoMapper extends Function<UserDto, UserEntity> {
}
