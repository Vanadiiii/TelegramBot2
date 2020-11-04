package ru.dexsys.user_controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.dexsys.domain.entity.UserEntity;
import ru.dexsys.user_controller.dto.UserUpdateDto;

@Mapper(componentModel = "spring")
public interface UserEntity2UpdateMapper {

    @Mapping(target = "birthDay", source = "birthday")
    UserUpdateDto apply(UserEntity userEntity);
}
