package ru.dexsys.user_controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.dexsys.domain.entity.UserEntity;
import ru.dexsys.user_controller.dto.UserFromMockDto;

@Mapper(componentModel = "spring")
public interface UserEntity2FromMockMapper {

    @Mapping(target = "birthDay", source = "birthday")
    UserFromMockDto apply(UserEntity userEntity);
}
